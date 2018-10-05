import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICartItems } from 'app/shared/model/cart-items.model';
import { Principal } from 'app/core';
import { CartItemsService } from './cart-items.service';

@Component({
    selector: 'jhi-cart-items',
    templateUrl: './cart-items.component.html'
})
export class CartItemsComponent implements OnInit, OnDestroy {
    cartItems: ICartItems[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private cartItemsService: CartItemsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.cartItemsService.query().subscribe(
            (res: HttpResponse<ICartItems[]>) => {
                this.cartItems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCartItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICartItems) {
        return item.id;
    }

    registerChangeInCartItems() {
        this.eventSubscriber = this.eventManager.subscribe('cartItemsListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
