import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IOrderList } from 'app/shared/model/order-list.model';
import { OrderListService } from './order-list.service';
import { IOrderStatus } from 'app/shared/model/order-status.model';
import { OrderStatusService } from 'app/entities/order-status';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile';
import { IfStmt } from '@angular/compiler';

@Component({
    selector: 'jhi-order-list-update',
    templateUrl: './order-list-update.component.html'
})
export class OrderListUpdateComponent implements OnInit {
    private _orderList: IOrderList;
    isSaving: boolean;

    orderstatuses: IOrderStatus[];

    userprofiles: IUserProfile[];
    lastUpdated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private orderListService: OrderListService,
        private orderStatusService: OrderStatusService,
        private userProfileService: UserProfileService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orderList }) => {
            this.orderList = orderList;
        });
        this.orderStatusService.query().subscribe(
            (res: HttpResponse<IOrderStatus[]>) => {
                this.orderstatuses = res.body;
                this.orderstatuses = this.orderstatuses.filter( o => {
                    if (this.orderList.orderStatus.name !== 'CANCELLED') {
                        if (o.name === 'CANCELLED') {
                            return o;
                        }
                        if (this.orderList.orderStatus.name === 'RECEIVED') {

                            if (o.name === 'CONFIRMED') {
                                return o;
                            }
                        }
                        if (this.orderList.orderStatus.name === 'CONFIRMED') {
                            if (o.name === 'DISPATCHED') {
                             return o;
                            }

                        }
                        if (this.orderList.orderStatus.name === 'DISPATCHED') {
                            if (o.name === 'DELIVERED') {
                             return o;
                            }
                        }
                        if (o.name === this.orderList.orderStatus.name) {
                            return o;
                        }
                    }
                });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.orderList.lastUpdated = moment(this.lastUpdated, DATE_TIME_FORMAT);
        if (this.orderList.id !== undefined) {
            this.subscribeToSaveResponse(this.orderListService.update(this.orderList));
        } else {
            this.subscribeToSaveResponse(this.orderListService.create(this.orderList));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrderList>>) {
        result.subscribe((res: HttpResponse<IOrderList>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOrderStatusById(index: number, item: IOrderStatus) {
        return item.id;
    }

    trackUserProfileById(index: number, item: IUserProfile) {
        return item.id;
    }
    get orderList() {
        return this._orderList;
    }

    set orderList(orderList: IOrderList) {
        this._orderList = orderList;
        this.lastUpdated = moment(orderList.lastUpdated).format(DATE_TIME_FORMAT);
    }
}
