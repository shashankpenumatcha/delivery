import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeliverySharedModule } from 'app/shared';
import {
    CartItemsComponent,
    CartItemsDetailComponent,
    CartItemsUpdateComponent,
    CartItemsDeletePopupComponent,
    CartItemsDeleteDialogComponent,
    cartItemsRoute,
    cartItemsPopupRoute
} from './';

const ENTITY_STATES = [...cartItemsRoute, ...cartItemsPopupRoute];

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CartItemsComponent,
        CartItemsDetailComponent,
        CartItemsUpdateComponent,
        CartItemsDeleteDialogComponent,
        CartItemsDeletePopupComponent
    ],
    entryComponents: [CartItemsComponent, CartItemsUpdateComponent, CartItemsDeleteDialogComponent, CartItemsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeliveryCartItemsModule {}
