import { Component, AfterViewInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { IOrderList } from 'app/shared/model/order-list.model';
import { IOrderTracker } from 'app/shared/model/order-tracker.model';

@Component({
    selector: 'jhi-order-tracker',
    templateUrl: './order-tracker.component.html',
    styleUrls: ['order-tracker.css']
})
export class OrderTrackerComponent implements AfterViewInit {
    order: IOrderList;
    orderTrackers: IOrderTracker[];
    orderId: number;
    loading = true;
    total: number;
    constructor(private http: HttpClient, private router: Router, private activeRoute: ActivatedRoute) {}
    ngAfterViewInit() {
        this.loading = true;
        this.activeRoute.params.subscribe( param => {
            this.orderId = param.id;
            this.http.get<any>('api/order-lists/' + param.id).subscribe(
                res => {
                    this.loading = false;
                    this.order = res;
                    this.total = this.getTotal(this.order);
                },
                res => {
                    this.loading = false;
                }
            );

            this.http.get<any>('api/order/track/' + param.id).subscribe(
                res => {
                    this.loading = false;
                    this.orderTrackers = res;
                },
                res => {
                    this.loading = false;
                }
            );
        });
    }

    getTotal(order: IOrderList): number {
        let price = 0;
        if (order.orderItems.length > 0) {
            order.orderItems.forEach(i => {
                price = price + i.price * i.quantity;
            });
        }
        return price;
    }
    back() {
        window.history.back();
    }
}
