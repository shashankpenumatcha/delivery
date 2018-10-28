import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { UserCartComponent } from './user-cart.component';

export const USER_CART_ROUTE: Route = {
    path: 'user-cart',
    component: UserCartComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Cart',
        animation: 'user-cart'
    },
    canActivate: [UserRouteAccessService]
};
