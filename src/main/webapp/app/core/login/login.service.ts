import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UserCartService } from 'app/shared/service/userCart.service';
import { UserAddressService } from '../../shared/service/userAddressService';

import { Principal } from '../auth/principal.service';
import { AuthServerProvider } from '../auth/auth-jwt.service';
import { JhiTrackerService } from '../tracker/tracker.service';
import { MessagingService } from 'app/shared/service/messaging.service';

@Injectable({ providedIn: 'root' })
export class LoginService {
    constructor(
        private router: Router,
        private principal: Principal,
        private trackerService: JhiTrackerService,
        private authServerProvider: AuthServerProvider,
        private cartService: UserCartService,
        private userAddressService: UserAddressService,
        private messagingService: MessagingService
    ) {}

    login(credentials, callback?) {
        const cb = callback || function() {};

        return new Promise((resolve, reject) => {
            this.authServerProvider.login(credentials).subscribe(
                data => {
                    this.cartService.setCart(null);
                    this.userAddressService.setUserAddresses(null);
                    try {
                        this.messagingService.init();
                    } catch (err) {
                        console.log(err);
                    }
                    this.principal.identity(true).then(account => {
                        this.trackerService.sendActivity();
                        resolve(data);
                    });
                    return cb();
                },
                err => {
                    this.logout();
                    reject(err);
                    return cb(err);
                }
            );
        });
    }

    loginWithToken(jwt, rememberMe) {
        return this.authServerProvider.loginWithToken(jwt, rememberMe);
    }

    logout() {
        this.messagingService.deleteToken();
        this.authServerProvider.logout().subscribe();
        this.principal.authenticate(null);
    }
}
