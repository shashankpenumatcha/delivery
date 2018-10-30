import { Component, AfterViewInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { IOrderList } from 'app/shared/model/order-list.model';

@Component({
    selector: 'jhi-dashboard-order-list',
    templateUrl: './order-list.component.html',
    styleUrls: ['order-list.css']
})
export class OrderListComponent implements AfterViewInit {
    type: string;
    orders: IOrderList[];
    constructor(private http: HttpClient, private router: Router, private activeRoute: ActivatedRoute) {}
    ngAfterViewInit() {
        this.activeRoute.params.subscribe(
            data => {
                if (data.type !== undefined && data.type !== null && ['received', 'confirmed', 'dispatched', 'delivered', 'cancelled'].indexOf(data.type) >= 0) {
                    this.type = data.type;
                    let param: string;
                    if (this.type === 'received') {
                        param = 'received&sort=createdDate,desc';
                    } else {
                        param = this.type;
                     }

                    this.http.get<any>('api/dashboard/orders/filter?status=' + param).subscribe(
                        res => {
                            this.orders = res.content;
                        }
                    );
                }
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
