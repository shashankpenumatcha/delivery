import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUnit } from 'app/shared/model/unit.model';
import { Principal } from 'app/core';
import { UnitService } from './unit.service';

@Component({
    selector: 'jhi-unit',
    templateUrl: './unit.component.html'
})
export class UnitComponent implements OnInit, OnDestroy {
    units: IUnit[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private unitService: UnitService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.unitService.query().subscribe(
            (res: HttpResponse<IUnit[]>) => {
                this.units = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUnits();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUnit) {
        return item.id;
    }

    registerChangeInUnits() {
        this.eventSubscriber = this.eventManager.subscribe('unitListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
