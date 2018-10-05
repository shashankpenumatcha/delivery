import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Unit } from 'app/shared/model/unit.model';
import { UnitService } from './unit.service';
import { UnitComponent } from './unit.component';
import { UnitDetailComponent } from './unit-detail.component';
import { UnitUpdateComponent } from './unit-update.component';
import { UnitDeletePopupComponent } from './unit-delete-dialog.component';
import { IUnit } from 'app/shared/model/unit.model';

@Injectable({ providedIn: 'root' })
export class UnitResolve implements Resolve<IUnit> {
    constructor(private service: UnitService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((unit: HttpResponse<Unit>) => unit.body));
        }
        return of(new Unit());
    }
}

export const unitRoute: Routes = [
    {
        path: 'unit',
        component: UnitComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Units'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unit/:id/view',
        component: UnitDetailComponent,
        resolve: {
            unit: UnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Units'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unit/new',
        component: UnitUpdateComponent,
        resolve: {
            unit: UnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Units'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'unit/:id/edit',
        component: UnitUpdateComponent,
        resolve: {
            unit: UnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Units'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const unitPopupRoute: Routes = [
    {
        path: 'unit/:id/delete',
        component: UnitDeletePopupComponent,
        resolve: {
            unit: UnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Units'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
