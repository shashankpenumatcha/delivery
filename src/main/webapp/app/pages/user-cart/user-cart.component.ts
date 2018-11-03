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
    total: number;
    cartLoading = false;
    loading = true;

    constructor(private http: HttpClient, private router: Router, private cartService: UserCartService) {}
    ngAfterViewInit() {
        this.cartService.loading.subscribe(l => {
            this.cartLoading = l;
        });
        this.loading = true;
        this.http.get('api/getCartForUser').subscribe(
            res => {
                this.loading = false;
                    this.cartService.setCart(res);
                    this.cartService.data.subscribe(cart => {
                        if (cart !== undefined) {
                            this.cart = cart;
                            if (this.cart !== null) {
                                this.setInCartFromCart(this.cart);
                            }
                        }
                    });
            },
            err => {
                this.loading = false;
            }
        );
    }

    setInCartFromCart(cart: ICart) {
        if (cart !== undefined && cart !== null && cart.cartItems !== undefined && cart.cartItems.length > 0) {
            this.total = 0;
            this.cart.cartItems = this.cart.cartItems.map(ci => {
                this.total += ci.product.pricePerUnit * ci.quantity;
                ci.product.inCart = ci.quantity;
                return ci;
            });
        }
    }

    placeOrder() {
        this.cartService.setLoading(true);
        this.http.post('api/placeOrder', {}).subscribe(
            res => {
                alert('order placed');
                this.cartService.setCart(null);
                this.cartService.setLoading(false);
            },
            err => {
                this.cartService.setLoading(false);
            }
        );
    }
}
