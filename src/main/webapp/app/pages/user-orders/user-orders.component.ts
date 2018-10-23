import { Component, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { IOrderList, OrderList } from 'app/shared/model/order-list.model';

@Component({
    selector: 'jhi-user-orders',
    templateUrl: './user-orders.component.html',
    styleUrls: ['user-orders.css']
})
export class UserOrdersComponent implements AfterViewInit {
    activeOrders: IOrderList[];
    loading = true;
    constructor(private http: HttpClient, private router: Router) {}
    ngAfterViewInit() {
        this.loading = true;
        this.http.get<any>('api/user/orders/active?sort=createdDate,desc').subscribe(
            res => {
                this.loading = false;
                this.activeOrders = res.content;
            },
            res => {
                this.loading = false;
            }
        );
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
}
