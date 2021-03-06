import { Injectable } from '@angular/core';
 import { AngularFireMessaging } from '@angular/fire/messaging';
import { BehaviorSubject } from 'rxjs';
import * as firebase from 'firebase';
import { HttpClient, HttpResponse } from '@angular/common/http';
import {  JhiAlertService } from 'ng-jhipster';
import { JhiTrackerService } from 'app/core';
import { Principal } from '../../core/auth/principal.service';

@Injectable({
    providedIn: 'root'
})
export class MessagingService {

  currentMessage = new BehaviorSubject(null);
  fcmToken: string;
  subscribed = false;

  constructor( private principal: Principal, private angularFireMessaging: AngularFireMessaging, private http: HttpClient, private jhiAlertService: JhiAlertService    ) {
    this.angularFireMessaging.messaging.subscribe(
      _messaging => {
        _messaging.onMessage = _messaging.onMessage.bind(_messaging);
        _messaging.onTokenRefresh = _messaging.onTokenRefresh.bind(_messaging);
      }
    );
  }

  updateToken(token) {
    // we can change this function to request our backend service
    localStorage.setItem('fcm', token);
        this.http.post('api/fcmToken/update?token=' + token, {}).subscribe (res => {
            this.fcmToken = token;
            this.principal.identity(true).then(account => {
                if (account !== null && account.authorities !== null && account.authorities.indexOf('ROLE_ADMIN') !== -1) {
                    this.http.get('api/fcm/topic/orders?token=' + this.fcmToken).subscribe( resp => {

                    });
                }
            });
        }, reason => {
            console.log(reason);
        });
  }

  requestPermission() {
    this.angularFireMessaging.requestToken.subscribe(
      token => {
        this.updateToken (token);
      },
      err => {
        console.error('Unable to get permission to notify.', err);
      }
    );
  }

  receiveMessage() {
    if ( !this.subscribed) {
    this.angularFireMessaging.messages.subscribe(
      payload => {
       let not: any;
       not = payload;
      /*  if (not.notification.body !== undefined) {
            alert(not.notification.body);
       } */
       this.subscribed = true;
        console.log('new message received. ', not);
        this.currentMessage.next(not.notification);
      });
    }
  }
  init() {
    this.requestPermission();
    this.receiveMessage();
    // this.initWebSockets();

  }

  deleteToken() {
    if (this.fcmToken !== undefined && this.fcmToken != null) {

        this.http.delete('api/fcm/topic/orders?token=' + this.fcmToken).subscribe( res => {
            this.http.delete('api/fcmToken/delete?token=' + this.fcmToken).subscribe(
                resp => {

                }
            );
        }, res => {
            this.http.delete('api/fcmToken/delete?token=' + this.fcmToken).subscribe(
                resp => {

                }
            );
        });
    }
  }
}
