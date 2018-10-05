import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IInventoryLog } from 'app/shared/model/inventory-log.model';
import { InventoryLogService } from './inventory-log.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile';

@Component({
    selector: 'jhi-inventory-log-update',
    templateUrl: './inventory-log-update.component.html'
})
export class InventoryLogUpdateComponent implements OnInit {
    private _inventoryLog: IInventoryLog;
    isSaving: boolean;

    products: IProduct[];

    userprofiles: IUserProfile[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private inventoryLogService: InventoryLogService,
        private productService: ProductService,
        private userProfileService: UserProfileService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ inventoryLog }) => {
            this.inventoryLog = inventoryLog;
        });
        this.productService.query().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.products = res.body;
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
        this.inventoryLog.date = moment(this.date, DATE_TIME_FORMAT);
        if (this.inventoryLog.id !== undefined) {
            this.subscribeToSaveResponse(this.inventoryLogService.update(this.inventoryLog));
        } else {
            this.subscribeToSaveResponse(this.inventoryLogService.create(this.inventoryLog));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryLog>>) {
        result.subscribe((res: HttpResponse<IInventoryLog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserProfileById(index: number, item: IUserProfile) {
        return item.id;
    }
    get inventoryLog() {
        return this._inventoryLog;
    }

    set inventoryLog(inventoryLog: IInventoryLog) {
        this._inventoryLog = inventoryLog;
        this.date = moment(inventoryLog.date).format(DATE_TIME_FORMAT);
    }
}
