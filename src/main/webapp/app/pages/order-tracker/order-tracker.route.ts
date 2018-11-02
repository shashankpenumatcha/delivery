import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { OrderTrackerComponent } from './order-tracker.component';

export const ORDER_TRACKER_ROUTE: Route = {
    path: 'order-tracker/:id',
    component: OrderTrackerComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Order-tracker',
        animation: 'order-tracker'
    },
    canActivate: [UserRouteAccessService]
};
