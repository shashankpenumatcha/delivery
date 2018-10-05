import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IOrderTracker } from 'app/shared/model/order-tracker.model';
import { OrderTrackerService } from './order-tracker.service';
import { IOrderList } from 'app/shared/model/order-list.model';
import { OrderListService } from 'app/entities/order-list';
import { IOrderStatus } from 'app/shared/model/order-status.model';
import { OrderStatusService } from 'app/entities/order-status';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile';

@Component({
    selector: 'jhi-order-tracker-update',
    templateUrl: './order-tracker-update.component.html'
})
export class OrderTrackerUpdateComponent implements OnInit {
    private _orderTracker: IOrderTracker;
    isSaving: boolean;

    orderlists: IOrderList[];

    orderstatuses: IOrderStatus[];

    userprofiles: IUserProfile[];
    dateTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private orderTrackerService: OrderTrackerService,
        private orderListService: OrderListService,
        private orderStatusService: OrderStatusService,
        private userProfileService: UserProfileService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orderTracker }) => {
            this.orderTracker = orderTracker;
        });
        this.orderListService.query().subscribe(
            (res: HttpResponse<IOrderList[]>) => {
                this.orderlists = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.orderStatusService.query().subscribe(
            (res: HttpResponse<IOrderStatus[]>) => {
                this.orderstatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userProfileService.query().subscribe(
            (res: HttpResponse<IUserProfile[]>) => {
                this.userprofiles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.orderTracker.dateTime = moment(this.dateTime, DATE_TIME_FORMAT);
        if (this.orderTracker.id !== undefined) {
            this.subscribeToSaveResponse(this.orderTrackerService.update(this.orderTracker));
        } else {
            this.subscribeToSaveResponse(this.orderTrackerService.create(this.orderTracker));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrderTracker>>) {
        result.subscribe((res: HttpResponse<IOrderTracker>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOrderListById(index: number, item: IOrderList) {
        return item.id;
    }

    trackOrderStatusById(index: number, item: IOrderStatus) {
        return item.id;
    }

    trackUserProfileById(index: number, item: IUserProfile) {
        return item.id;
    }
    get orderTracker() {
        return this._orderTracker;
    }

    set orderTracker(orderTracker: IOrderTracker) {
        this._orderTracker = orderTracker;
        this.dateTime = moment(orderTracker.dateTime).format(DATE_TIME_FORMAT);
    }
}
