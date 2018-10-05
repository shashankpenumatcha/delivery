import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICartItems } from 'app/shared/model/cart-items.model';

@Component({
    selector: 'jhi-cart-items-detail',
    templateUrl: './cart-items-detail.component.html'
})
export class CartItemsDetailComponent implements OnInit {
    cartItems: ICartItems;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cartItems }) => {
            this.cartItems = cartItems;
        });
    }

    previousState() {
        window.history.back();
    }
}
