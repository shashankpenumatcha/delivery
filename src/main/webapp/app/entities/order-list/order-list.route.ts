import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { OrderList } from 'app/shared/model/order-list.model';
import { OrderListService } from './order-list.service';
import { OrderListComponent } from './order-list.component';
import { OrderListDetailComponent } from './order-list-detail.component';
import { OrderListUpdateComponent } from './order-list-update.component';
import { OrderListDeletePopupComponent } from './order-list-delete-dialog.component';
import { IOrderList } from 'app/shared/model/order-list.model';

@Injectable({ providedIn: 'root' })
export class OrderListResolve implements Resolve<IOrderList> {
    constructor(private service: OrderListService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((orderList: HttpResponse<OrderList>) => orderList.body));
        }
        return of(new OrderList());
    }
}

export const orderListRoute: Routes = [
    {
        path: 'order-list',
        component: OrderListComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderLists'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-list/:id/view',
        component: OrderListDetailComponent,
        resolve: {
            orderList: OrderListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderLists'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-list/new',
        component: OrderListUpdateComponent,
        resolve: {
            orderList: OrderListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderLists'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-list/:id/edit',
        component: OrderListUpdateComponent,
        resolve: {
            orderList: OrderListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderLists'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderListPopupRoute: Routes = [
    {
        path: 'order-list/:id/delete',
        component: OrderListDeletePopupComponent,
        resolve: {
            orderList: OrderListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderLists'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
