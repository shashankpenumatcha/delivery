import {Input, Directive, Component, OnInit, AfterViewInit, Renderer, ElementRef, ViewChild } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import {Validator,AbstractControl,ValidatorFn,NgForm,NG_VALIDATORS} from '@angular/forms';
import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared';
import { LoginModalService } from 'app/core';
import { Register } from './register.service';
/* import { VerifyPhoneComponent } from '../../shared/verify-phone/verify-phone.component';
 */ import { LoginService } from 'app/core/login/login.service';

@Component({
    selector: 'jhi-register',
    templateUrl: './register.component.html',
    styleUrls: ['register.css']
})
export class RegisterComponent implements OnInit, AfterViewInit {

    confirmPassword: string;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    success: boolean;
    modalRef: NgbModalRef;
    verified = false;
    verifyClicked = false;

    constructor(
        private loginModalService: LoginModalService,
        private registerService: Register,
        private elementRef: ElementRef,
        private renderer: Renderer,
        private modalService: NgbModal,
        private router: Router,
        private loginService: LoginService
    ) {}

    ngOnInit() {
        this.success = false;
        this.registerAccount = {};
    }

    ngAfterViewInit() {
        this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
    }

    verify() {
        this.verifyClicked = true;
    }

    onMobileNumber(){
        this.verified = false;
        this.verifyClicked = false;
    }

    submitOTP(res: any) {
        let verified = res;
        if (res === undefined || res === null) {
            verified = false;
        }
        this.verified = verified;
    }

    register() {
        if (this.registerAccount.password !== this.confirmPassword) {
            this.doNotMatch = 'ERROR';
        } else {
            this.doNotMatch = null;
            this.error = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.registerAccount.langKey = 'en';
            this.registerAccount.login = this.registerAccount.phoneNumber;
            this.registerService.save(this.registerAccount).subscribe(
                () => {
                    this.loginService
                        .login({
                            username: this.registerAccount.login,
                            password: this.registerAccount.password,
                            rememberMe: true
                        })
                        .then(() => {
                            this.router.navigate(['/']);
                        })
                        .catch(() => {
                            this.success = true;
                        });
                },
                response => this.processError(response)
            );
        }
    }

    openLogin() {
        this.modalRef = this.loginModalService.open();
    }

    private processError(response: HttpErrorResponse) {
        this.success = null;
        if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
            this.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }
}
