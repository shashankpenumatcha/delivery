import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { ProductsComponent } from './products.component';

export const PRODUCTS_ROUTE: Route = {
    path: 'dashboard/products',
    component: ProductsComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'Dashboard',
        animation: 'dashboard-products'
    },
    canActivate: [UserRouteAccessService]
};
