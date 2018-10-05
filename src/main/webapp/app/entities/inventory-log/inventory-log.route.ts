import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { InventoryLog } from 'app/shared/model/inventory-log.model';
import { InventoryLogService } from './inventory-log.service';
import { InventoryLogComponent } from './inventory-log.component';
import { InventoryLogDetailComponent } from './inventory-log-detail.component';
import { InventoryLogUpdateComponent } from './inventory-log-update.component';
import { InventoryLogDeletePopupComponent } from './inventory-log-delete-dialog.component';
import { IInventoryLog } from 'app/shared/model/inventory-log.model';

@Injectable({ providedIn: 'root' })
export class InventoryLogResolve implements Resolve<IInventoryLog> {
    constructor(private service: InventoryLogService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((inventoryLog: HttpResponse<InventoryLog>) => inventoryLog.body));
        }
        return of(new InventoryLog());
    }
}

export const inventoryLogRoute: Routes = [
    {
        path: 'inventory-log',
        component: InventoryLogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'inventory-log/:id/view',
        component: InventoryLogDetailComponent,
        resolve: {
            inventoryLog: InventoryLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'inventory-log/new',
        component: InventoryLogUpdateComponent,
        resolve: {
            inventoryLog: InventoryLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'inventory-log/:id/edit',
        component: InventoryLogUpdateComponent,
        resolve: {
            inventoryLog: InventoryLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLogs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventoryLogPopupRoute: Routes = [
    {
        path: 'inventory-log/:id/delete',
        component: InventoryLogDeletePopupComponent,
        resolve: {
            inventoryLog: InventoryLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InventoryLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
