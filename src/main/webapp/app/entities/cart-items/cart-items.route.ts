import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CartItems } from 'app/shared/model/cart-items.model';
import { CartItemsService } from './cart-items.service';
import { CartItemsComponent } from './cart-items.component';
import { CartItemsDetailComponent } from './cart-items-detail.component';
import { CartItemsUpdateComponent } from './cart-items-update.component';
import { CartItemsDeletePopupComponent } from './cart-items-delete-dialog.component';
import { ICartItems } from 'app/shared/model/cart-items.model';

@Injectable({ providedIn: 'root' })
export class CartItemsResolve implements Resolve<ICartItems> {
    constructor(private service: CartItemsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((cartItems: HttpResponse<CartItems>) => cartItems.body));
        }
        return of(new CartItems());
    }
}

export const cartItemsRoute: Routes = [
    {
        path: 'cart-items',
        component: CartItemsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CartItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cart-items/:id/view',
        component: CartItemsDetailComponent,
        resolve: {
            cartItems: CartItemsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CartItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cart-items/new',
        component: CartItemsUpdateComponent,
        resolve: {
            cartItems: CartItemsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CartItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cart-items/:id/edit',
        component: CartItemsUpdateComponent,
        resolve: {
            cartItems: CartItemsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CartItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cartItemsPopupRoute: Routes = [
    {
        path: 'cart-items/:id/delete',
        component: CartItemsDeletePopupComponent,
        resolve: {
            cartItems: CartItemsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CartItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
