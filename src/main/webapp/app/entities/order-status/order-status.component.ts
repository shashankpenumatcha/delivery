import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOrderStatus } from 'app/shared/model/order-status.model';
import { Principal } from 'app/core';
import { OrderStatusService } from './order-status.service';

@Component({
    selector: 'jhi-order-status',
    templateUrl: './order-status.component.html'
})
export class OrderStatusComponent implements OnInit, OnDestroy {
    orderStatuses: IOrderStatus[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private orderStatusService: OrderStatusService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.orderStatusService.query().subscribe(
            (res: HttpResponse<IOrderStatus[]>) => {
                this.orderStatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOrderStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOrderStatus) {
        return item.id;
    }

    registerChangeInOrderStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('orderStatusListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
