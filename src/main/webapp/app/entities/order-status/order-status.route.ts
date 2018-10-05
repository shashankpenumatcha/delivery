import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { OrderStatus } from 'app/shared/model/order-status.model';
import { OrderStatusService } from './order-status.service';
import { OrderStatusComponent } from './order-status.component';
import { OrderStatusDetailComponent } from './order-status-detail.component';
import { OrderStatusUpdateComponent } from './order-status-update.component';
import { OrderStatusDeletePopupComponent } from './order-status-delete-dialog.component';
import { IOrderStatus } from 'app/shared/model/order-status.model';

@Injectable({ providedIn: 'root' })
export class OrderStatusResolve implements Resolve<IOrderStatus> {
    constructor(private service: OrderStatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((orderStatus: HttpResponse<OrderStatus>) => orderStatus.body));
        }
        return of(new OrderStatus());
    }
}

export const orderStatusRoute: Routes = [
    {
        path: 'order-status',
        component: OrderStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-status/:id/view',
        component: OrderStatusDetailComponent,
        resolve: {
            orderStatus: OrderStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-status/new',
        component: OrderStatusUpdateComponent,
        resolve: {
            orderStatus: OrderStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-status/:id/edit',
        component: OrderStatusUpdateComponent,
        resolve: {
            orderStatus: OrderStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderStatusPopupRoute: Routes = [
    {
        path: 'order-status/:id/delete',
        component: OrderStatusDeletePopupComponent,
        resolve: {
            orderStatus: OrderStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
