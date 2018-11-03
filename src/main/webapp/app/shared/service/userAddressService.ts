import { Injectable } from '@angular/core';
import { IUserAddress, UserAddress } from 'app/shared/model/user-address.module';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UserAddressService {
    private _userAddresses: IUserAddress[];
    public userAddresses = new BehaviorSubject(this._userAddresses);
    public loading = new BehaviorSubject(false);

    constructor(private http: HttpClient) {}

    loadUserAddresses() {
        return this.http.get<any>('api/user/addresses');
    }

    getUserAddresses() {
        return this._userAddresses;
    }

    setUserAddresses(userAddresses: IUserAddress[]) {
        this._userAddresses = userAddresses;
        this.userAddresses.next(this._userAddresses);
    }

    setLoading(loading: boolean) {
        this.loading.next(loading);
    }
}
