import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { ListingComponent } from './';

export const LISTING_ROUTE: Route = {
    path: 'listing',
    component: ListingComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Products',
        animation: 'listing'
    },
    canActivate: [UserRouteAccessService]
};
