import { Component, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-dashboard-orders',
    templateUrl: './orders.component.html',
    styleUrls: ['orders.css']
})
export class OrdersComponent implements AfterViewInit {
    orderCount: any;
    constructor(private http: HttpClient, private router: Router) {}
    ngAfterViewInit() {
        this.http.get('api/dashboard/orders/count').subscribe(
            res => {
                this.orderCount = res;
            }
        );
    }
}
