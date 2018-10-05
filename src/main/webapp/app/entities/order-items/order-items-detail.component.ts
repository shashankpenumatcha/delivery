import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderItems } from 'app/shared/model/order-items.model';

@Component({
    selector: 'jhi-order-items-detail',
    templateUrl: './order-items-detail.component.html'
})
export class OrderItemsDetailComponent implements OnInit {
    orderItems: IOrderItems;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orderItems }) => {
            this.orderItems = orderItems;
        });
    }

    previousState() {
        window.history.back();
    }
}
