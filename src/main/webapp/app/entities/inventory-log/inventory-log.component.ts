import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInventoryLog } from 'app/shared/model/inventory-log.model';
import { Principal } from 'app/core';
import { InventoryLogService } from './inventory-log.service';

@Component({
    selector: 'jhi-inventory-log',
    templateUrl: './inventory-log.component.html'
})
export class InventoryLogComponent implements OnInit, OnDestroy {
    inventoryLogs: IInventoryLog[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private inventoryLogService: InventoryLogService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.inventoryLogService.query().subscribe(
            (res: HttpResponse<IInventoryLog[]>) => {
                this.inventoryLogs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInInventoryLogs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IInventoryLog) {
        return item.id;
    }

    registerChangeInInventoryLogs() {
        this.eventSubscriber = this.eventManager.subscribe('inventoryLogListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
