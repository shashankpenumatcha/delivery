import { Injectable } from '@angular/core';
import { ICart, Cart } from 'app/shared/model//cart.model';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UserCartService {
    private _cart?: ICart;

    constructor(private http: HttpClient) {}

    loadCart() {
        return this.http.get('api/getCartForUser');
    }

    getCart() {
        return this._cart;
    }

    setCart(cart: ICart) {
        this._cart = cart;
    }
}
