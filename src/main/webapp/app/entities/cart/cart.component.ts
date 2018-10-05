import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICart } from 'app/shared/model/cart.model';
import { Principal } from 'app/core';
import { CartService } from './cart.service';

@Component({
    selector: 'jhi-cart',
    templateUrl: './cart.component.html'
})
export class CartComponent implements OnInit, OnDestroy {
    carts: ICart[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private cartService: CartService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.cartService.query().subscribe(
            (res: HttpResponse<ICart[]>) => {
                this.carts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCarts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICart) {
        return item.id;
    }

    registerChangeInCarts() {
        this.eventSubscriber = this.eventManager.subscribe('cartListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
