import { Component, AfterViewInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { UserAddressService } from '../service/userAddressService';
import { StateStorageService } from 'app/core/auth/state-storage.service';

@Component({
    selector: 'jhi-add-address',
    templateUrl: './add-address.html'
})
export class AddAddressComponent implements AfterViewInit {

    address1: string;
    address2: string;
    address3: string;
    address4: string;
    address: string;
    constructor(
        private userAddressService: UserAddressService,
        private router: Router,
        public activeModal: NgbActiveModal,
        private http: HttpClient
    ) {
    }

    ngAfterViewInit() {
    }

    cancel() {
        this.activeModal.dismiss('cancel');
    }

    add() {
        this.address = '';
        if (this.address1 !== undefined && this.address1 !== null && this.address1.length > 0) {
            this.address1 = this.address1.replace( new RegExp(',' , 'g') , ' ');
        } else {
            this.address1 = '-';
        }
        if (this.address2 !== undefined && this.address2 !== null && this.address2.length > 0) {
            this.address2 = this.address2.replace(new RegExp(',' , 'g') , ' ');
        } else {
            this.address2 = '-';
        }
        if (this.address3 !== undefined && this.address3 !== null && this.address3.length > 0) {
            this.address3 = this.address3.replace(new RegExp(',' , 'g') , ' ');
        } else {
            this.address3 = '-';
        }
        if (this.address4 !== undefined && this.address4 !== null && this.address4.length > 0) {
            this.address4 = this.address4.replace(new RegExp(',' , 'g') , ' ');
        } else {
            this.address4 = '-';
        }
        this.address = this.address1 + ';' + this.address2 + ';' + this.address3 + ';' + this.address4;
        this.http.post('api/user-address' , {address : this.address}).subscribe(
            res => {
                this.userAddressService.loadUserAddresses().subscribe(
                    add => {
                        this.userAddressService.setUserAddresses(add);
                        this.activeModal.dismiss('cancel');

                    }
                );
            }
        );
    }

}
