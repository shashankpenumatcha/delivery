import { Injectable } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/messaging';
import { BehaviorSubject } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class MessagingService {

  currentMessage = new BehaviorSubject(null);

  constructor(private angularFireMessaging: AngularFireMessaging) {
    this.angularFireMessaging.messaging.subscribe(
      _messaging => {
        _messaging.onMessage = _messaging.onMessage.bind(_messaging);
        _messaging.onTokenRefresh = _messaging.onTokenRefresh.bind(_messaging);
      }
    );
  }

  updateToken( token) {
    // we can change this function to request our backend service
        localStorage.setItem('fcm', token);

  }

  requestPermission() {
    this.angularFireMessaging.requestToken.subscribe(
      token => {
        this.updateToken( token);
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
        console.log('new message received. ', not);
        this.currentMessage.next(not.notification);
      });
  }
}
