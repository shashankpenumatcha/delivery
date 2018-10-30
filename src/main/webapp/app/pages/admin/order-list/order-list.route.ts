import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { OrderListComponent } from './order-list.component';

export const ORDER_LIST_ROUTE: Route = {
    path: 'dashboard/orders/:type',
    component: OrderListComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'Dashboard - Order list',
        animation: 'dashboard-order-list'
    },
    canActivate: [UserRouteAccessService]
};
