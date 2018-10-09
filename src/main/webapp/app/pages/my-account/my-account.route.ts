import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { MyAccountComponent } from '.';

export const MY_ACCCOUNT_ROUTE: Route = {
    path: 'my-account',
    component: MyAccountComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Account'
    },
    canActivate: [UserRouteAccessService]
};
