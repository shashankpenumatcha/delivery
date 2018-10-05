import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderList } from 'app/shared/model/order-list.model';

@Component({
    selector: 'jhi-order-list-detail',
    templateUrl: './order-list-detail.component.html'
})
export class OrderListDetailComponent implements OnInit {
    orderList: IOrderList;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orderList }) => {
            this.orderList = orderList;
        });
    }

    previousState() {
        window.history.back();
    }
}
