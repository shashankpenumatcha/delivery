import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { OrdersComponent } from './orders.component';

export const ORDERS_ROUTE: Route = {
    path: 'dashboard/orders',
    component: OrdersComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'Dashboard - orders',
        animation: 'dashboard-orders'
    },
    canActivate: [UserRouteAccessService]
};
