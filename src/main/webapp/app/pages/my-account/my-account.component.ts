import { Component, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/core/login/login.service';

@Component({
    selector: 'jhi-my-account',
    templateUrl: './my-account.html',
    styleUrls: ['myAccount.css']
})
export class MyAccountComponent implements AfterViewInit {
    account: any;
    constructor(
        private http: HttpClient,
        private accountService: AccountService,
        private loginService: LoginService,
        private router: Router
    ) {}

    ngAfterViewInit() {
        this.accountService.get().subscribe((res: any) => {
            if (res.body !== null) {
                this.account = res.body;
            }
        });
    }

    logout() {
        this.loginService.logout();
        this.router.navigate(['']);
    }
}
