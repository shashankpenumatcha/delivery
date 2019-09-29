import { Component, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/core/login/login.service';
import { IUserAddress, UserAddress } from '../../shared/model/user-address.module';
import { UserAddressService } from '../../shared/service/userAddressService';
import { AddAddressComponent } from '../../shared/add-address/add-address.component';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'address'
})
export class AddressPipe implements PipeTransform {
  transform(value: string): string {
    let trans = value;
    trans = trans.replace(new RegExp(';', 'g'), ', ');
    trans = trans.replace(new RegExp(', -', 'g'), '');

    return trans;
  }
}

@Component({
    selector: 'jhi-my-account',
    templateUrl: './my-account.html',
    styleUrls: ['myAccount.css']
})
export class MyAccountComponent implements AfterViewInit {
    account: any;
    showAddress = true;
    userAddresses: IUserAddress[];
    private isOpen = false;
    constructor(
        private http: HttpClient,
        private accountService: AccountService,
        private loginService: LoginService,
        private router: Router,
        private userAddressService: UserAddressService,
        private modalService: NgbModal
    ) {}

    openAddAddress() {

        const modalRef = this.modalService.open(AddAddressComponent,  { centered: true, size: 'lg' });

    }
    ngAfterViewInit() {
        this.accountService.get().subscribe((res: any) => {
            if (res.body !== null) {
                this.account = res.body;
            }
        });

        this.userAddressService.userAddresses.subscribe( res => {
            this.userAddresses = res;
        });

        this.userAddressService.loadUserAddresses().subscribe(
            res => {
                this.userAddressService.setUserAddresses(res);
            }
        );
    }

    logout() {
        this.loginService.logout();
        this.router.navigate(['']);
    }

    delete(id: number) {
        this.http.delete('api/user-address/' + id).subscribe(
            res => {
                this.userAddressService.loadUserAddresses().subscribe(
                    add => {
                        this.userAddressService.setUserAddresses(add);
                    }
                );
            }
        );
    }
}
