import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { OrderTracker } from 'app/shared/model/order-tracker.model';
import { OrderTrackerService } from './order-tracker.service';
import { OrderTrackerComponent } from './order-tracker.component';
import { OrderTrackerDetailComponent } from './order-tracker-detail.component';
import { OrderTrackerUpdateComponent } from './order-tracker-update.component';
import { OrderTrackerDeletePopupComponent } from './order-tracker-delete-dialog.component';
import { IOrderTracker } from 'app/shared/model/order-tracker.model';

@Injectable({ providedIn: 'root' })
export class OrderTrackerResolve implements Resolve<IOrderTracker> {
    constructor(private service: OrderTrackerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((orderTracker: HttpResponse<OrderTracker>) => orderTracker.body));
        }
        return of(new OrderTracker());
    }
}

export const orderTrackerRoute: Routes = [
    {
        path: 'order-tracker',
        component: OrderTrackerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderTrackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-tracker/:id/view',
        component: OrderTrackerDetailComponent,
        resolve: {
            orderTracker: OrderTrackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderTrackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-tracker/new',
        component: OrderTrackerUpdateComponent,
        resolve: {
            orderTracker: OrderTrackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderTrackers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-tracker/:id/edit',
        component: OrderTrackerUpdateComponent,
        resolve: {
            orderTracker: OrderTrackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderTrackers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderTrackerPopupRoute: Routes = [
    {
        path: 'order-tracker/:id/delete',
        component: OrderTrackerDeletePopupComponent,
        resolve: {
            orderTracker: OrderTrackerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderTrackers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
