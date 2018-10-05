import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOrderItems } from 'app/shared/model/order-items.model';
import { OrderItemsService } from './order-items.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { IOrderList } from 'app/shared/model/order-list.model';
import { OrderListService } from 'app/entities/order-list';

@Component({
    selector: 'jhi-order-items-update',
    templateUrl: './order-items-update.component.html'
})
export class OrderItemsUpdateComponent implements OnInit {
    private _orderItems: IOrderItems;
    isSaving: boolean;

    products: IProduct[];

    orderlists: IOrderList[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private orderItemsService: OrderItemsService,
        private productService: ProductService,
        private orderListService: OrderListService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orderItems }) => {
            this.orderItems = orderItems;
        });
        this.productService.query().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.products = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.orderListService.query().subscribe(
            (res: HttpResponse<IOrderList[]>) => {
                this.orderlists = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.orderItems.id !== undefined) {
            this.subscribeToSaveResponse(this.orderItemsService.update(this.orderItems));
        } else {
            this.subscribeToSaveResponse(this.orderItemsService.create(this.orderItems));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrderItems>>) {
        result.subscribe((res: HttpResponse<IOrderItems>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOrderListById(index: number, item: IOrderList) {
        return item.id;
    }
    get orderItems() {
        return this._orderItems;
    }

    set orderItems(orderItems: IOrderItems) {
        this._orderItems = orderItems;
    }
}
