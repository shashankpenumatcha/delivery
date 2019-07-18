import { Injectable } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { Observable, Observer, Subscription } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { CSRFService } from '../auth/csrf.service';
import { WindowRef } from './window.service';
import { AuthServerProvider } from '../auth/auth-jwt.service';
import { NgxNotificationService } from 'ngx-notification';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'webstomp-client';
import { IUserProfile } from 'app/shared/model/user-profile.model';

@Injectable({ providedIn: 'root' })
export class JhiTrackerService {
    stompClient = null;
    subscriber = null;
    orderSubscriber = null;
    connection: Promise<any>;
    connectedPromise: any;
    listener: Observable<any>;
    listenerObserver: Observer<any>;
    alreadyConnectedOnce = false;
    subscribed = false;
    userProfile: IUserProfile;
    private subscription: Subscription;

    constructor(
        private http: HttpClient,
        private router: Router,
        private authServerProvider: AuthServerProvider,
        private $window: WindowRef,
        // tslint:disable-next-line: no-unused-variable
        private csrfService: CSRFService,
        private ngxNotificationService: NgxNotificationService
    ) {
        this.connection = this.createConnection();
        this.listener = this.createListener();
    }

    connect() {
        if (this.connectedPromise === null) {
            this.connection = this.createConnection();
        }
        // building absolute path so that websocket doesn't fail when deploying with a context path
        const loc = this.$window.nativeWindow.location;
        let url;
        url = '//' + loc.host + loc.pathname + 'websocket/tracker';
     // url = '//localhost:8080/websocket/tracker';

        const authToken = this.authServerProvider.getToken();
        if (authToken) {
            url += '?access_token=' + authToken;
        }

        const socket = new SockJS(url);
        this.stompClient = Stomp.over(socket);
        const headers = {};
        this.stompClient.connect(
            headers,
            () => {
                 this.connectedPromise('success');
                this.connectedPromise = null;
              //  this.sendActivity();
              /*   if (!this.alreadyConnectedOnce) {
                    this.subscription = this.router.events.subscribe(event => {
                        if (event instanceof NavigationEnd) {
                            this.sendActivity();
                        }
                    });
                    this.alreadyConnectedOnce = true;
                } */
            }
        );

    }

    disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
            this.stompClient = null;
        }
        if (this.subscription) {
            this.subscription.unsubscribe();
            this.subscription = null;
        }
        this.alreadyConnectedOnce = false;
    }

    receive() {
        return this.listener;
    }

    sendActivity() {
        if (this.stompClient !== null && this.stompClient.connected) {
            this.stompClient.send(
                '/topic/activity', // destination
                JSON.stringify({ page: this.router.routerState.snapshot.url }), // body
                {} // header
            );
        }
    }

    subscribe() {
        if (!this.subscribed) {
        this.connection.then(() => {
            this.subscriber = this.stompClient.subscribe('/topic/orders', data => {
                this.listenerObserver.next(JSON.parse(data.body));
                this.subscribed = true;

            });
        });
    }
    }

    subscribeOrder() {
        this.http.get<IUserProfile>('api/userProfile/current').subscribe( res => {
            if (res !== undefined && res !== null && res.id !== undefined) {
                this.connection.then(() => {
                    this.orderSubscriber = this.stompClient.subscribe('/topic/orders/' + res.id, data => {
                       // this.listenerObserver.next(JSON.parse(data.body));
                       const order = JSON.parse(data.body);
                       if (order !== undefined && order.orderStatus !== undefined) {
                           this.playAudio();
                            this.ngxNotificationService.sendMessage('Your order# ' +  order.id + ' is ' + order.orderStatus.name, 'dark', 'top-center');

                       }
                    });
                });
            }
        });

    }

    playAudio() {
        const audio = new Audio();
        audio.src = '../../../content/bell.wav';
        audio.load();
        audio.play();
      }

    unsubscribe() {
        if (this.subscriber !== null) {
            this.subscriber.unsubscribe();
        }
        if (this.orderSubscriber !== null) {
            this.orderSubscriber.unsubscribe();
        }
        this.listener = this.createListener();
    }

    private createListener(): Observable<any> {
        return new Observable(observer => {
            this.listenerObserver = observer;
        });
    }

    private createConnection(): Promise<any> {
        return new Promise((resolve, reject) => (this.connectedPromise = resolve));
    }
}
