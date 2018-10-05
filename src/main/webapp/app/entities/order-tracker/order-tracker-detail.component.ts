import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderTracker } from 'app/shared/model/order-tracker.model';

@Component({
    selector: 'jhi-order-tracker-detail',
    templateUrl: './order-tracker-detail.component.html'
})
export class OrderTrackerDetailComponent implements OnInit {
    orderTracker: IOrderTracker;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orderTracker }) => {
            this.orderTracker = orderTracker;
        });
    }

    previousState() {
        window.history.back();
    }
}
