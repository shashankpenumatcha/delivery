import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOrderItems } from 'app/shared/model/order-items.model';
import { Principal } from 'app/core';
import { OrderItemsService } from './order-items.service';

@Component({
    selector: 'jhi-order-items',
    templateUrl: './order-items.component.html'
})
export class OrderItemsComponent implements OnInit, OnDestroy {
    orderItems: IOrderItems[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private orderItemsService: OrderItemsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.orderItemsService.query().subscribe(
            (res: HttpResponse<IOrderItems[]>) => {
                this.orderItems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOrderItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOrderItems) {
        return item.id;
    }

    registerChangeInOrderItems() {
        this.eventSubscriber = this.eventManager.subscribe('orderItemsListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
