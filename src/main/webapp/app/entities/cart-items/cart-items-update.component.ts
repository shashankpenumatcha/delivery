import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICartItems } from 'app/shared/model/cart-items.model';
import { CartItemsService } from './cart-items.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { ICart } from 'app/shared/model/cart.model';
import { CartService } from 'app/entities/cart';

@Component({
    selector: 'jhi-cart-items-update',
    templateUrl: './cart-items-update.component.html'
})
export class CartItemsUpdateComponent implements OnInit {
    private _cartItems: ICartItems;
    isSaving: boolean;

    products: IProduct[];

    carts: ICart[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private cartItemsService: CartItemsService,
        private productService: ProductService,
        private cartService: CartService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cartItems }) => {
            this.cartItems = cartItems;
        });
        this.productService.query().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.products = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.cartService.query().subscribe(
            (res: HttpResponse<ICart[]>) => {
                this.carts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cartItems.id !== undefined) {
            this.subscribeToSaveResponse(this.cartItemsService.update(this.cartItems));
        } else {
            this.subscribeToSaveResponse(this.cartItemsService.create(this.cartItems));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICartItems>>) {
        result.subscribe((res: HttpResponse<ICartItems>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }

    trackCartById(index: number, item: ICart) {
        return item.id;
    }
    get cartItems() {
        return this._cartItems;
    }

    set cartItems(cartItems: ICartItems) {
        this._cartItems = cartItems;
    }
}
