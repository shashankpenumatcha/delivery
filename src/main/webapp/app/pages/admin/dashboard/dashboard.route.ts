import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { DashboardComponent } from './dashboard.component';

export const DASHBOARD_ROUTE: Route = {
    path: 'dashboard',
    component: DashboardComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'Dashboard'
    },
    canActivate: [UserRouteAccessService]
};
