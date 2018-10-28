import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { UserOrdersComponent } from './user-orders.component';

export const USER_ORDERS_ROUTE: Route = {
    path: 'user-orders',
    component: UserOrdersComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Orders',
        animation: 'user-orders'
    },
    canActivate: [UserRouteAccessService]
};
