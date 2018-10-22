import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { VERSION } from 'app/app.constants';
import { Principal, LoginModalService, LoginService } from 'app/core';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { UserCartService } from 'app/shared/service/userCart.service';
import { ICart } from 'app/shared/model/cart.model';

@Component({
    selector: 'jhi-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['menu.css']
})
export class MenuComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    cartCount: number;

    constructor(
        private loginService: LoginService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        public router: Router,
        private cartService: UserCartService
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    ngOnInit() {
        this.profileService.getProfileInfo().then(profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });

        this.cartService.data.subscribe(c => {
            if (c === null) {
                this.cartCount = 0;
            }
            if (c !== undefined && c !== null && c.cartItems !== undefined) {
                this.cartCount = c.cartItems.length;
            }
        });
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }
}
