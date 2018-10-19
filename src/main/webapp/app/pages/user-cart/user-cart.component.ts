import { Component, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ICart } from 'app/shared/model/cart.model';
import { UserCartService } from 'app/shared/service/userCart.service';
import { CartItems } from 'app/shared/model/cart-items.model';

@Component({
    selector: 'jhi-user-cart',
    templateUrl: './user-cart.component.html',
    styleUrls: ['user-cart.css']
})
export class UserCartComponent implements AfterViewInit {
    cart: ICart;
    constructor(private http: HttpClient, private router: Router, private cartService: UserCartService) {}

    ngAfterViewInit() {
        this.http.get('api/getCartForUser').subscribe(res => {
            if (res !== null) {
                this.cartService.setCart(res);
                this.cartService.data.subscribe(cart => {
                    if (cart !== undefined && cart !== null) {
                        this.cart = cart;
                        this.setInCartFromCart(this.cart);
                    }
                });
            }
        });
    }

    setInCartFromCart(cart: ICart) {
        if (cart !== undefined && cart !== null && cart.cartItems !== undefined && cart.cartItems.length > 0) {
            this.cart.cartItems = this.cart.cartItems.map(ci => {
                ci.product.inCart = ci.quantity;
                return ci;
            });
        }
    }
}
