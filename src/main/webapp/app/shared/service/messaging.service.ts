import { Injectable } from '@angular/core';
 import { AngularFireMessaging } from '@angular/fire/messaging';
import { BehaviorSubject } from 'rxjs';
import * as firebase from 'firebase';
import { HttpClient, HttpResponse } from '@angular/common/http';
import {  JhiAlertService } from 'ng-jhipster';

@Injectable({
    providedIn: 'root'
})
export class MessagingService {

  currentMessage = new BehaviorSubject(null);
  fcmToken: string;

  constructor(private angularFireMessaging: AngularFireMessaging, private http: HttpClient, private jhiAlertService: JhiAlertService,
    ) {
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
    this.angularFireMessaging.messages.subscribe(
      payload => {
       let not: any;
       not = payload;
       if (not.notification.body !== undefined) {
            alert(not.notification.body);
       }

        console.log('new message received. ', not);
        this.currentMessage.next(not.notification);
      });
  }
  init() {
    this.requestPermission();
    this.receiveMessage();
  }

  deleteToken() {
    if (this.fcmToken !== undefined && this.fcmToken != null) {
        this.http.delete('api/fcmToken/delete?token=' + this.fcmToken).subscribe(
            res => {

            }
        );
    }
  }
}
