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
    constructor(private http: HttpClient, private router: Router) {}
    ngAfterViewInit() {
        this.http.get<IOrderList[]>('api/user/orders/active?sort=id,desc').subscribe(res => {
            this.activeOrders = res;
        });
    }
}
