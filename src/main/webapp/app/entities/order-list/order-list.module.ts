import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeliverySharedModule } from 'app/shared';
import {
    OrderListComponent,
    OrderListDetailComponent,
    OrderListUpdateComponent,
    OrderListDeletePopupComponent,
    OrderListDeleteDialogComponent,
    orderListRoute,
    orderListPopupRoute
} from './';

const ENTITY_STATES = [...orderListRoute, ...orderListPopupRoute];

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OrderListComponent,
        OrderListDetailComponent,
        OrderListUpdateComponent,
        OrderListDeleteDialogComponent,
        OrderListDeletePopupComponent
    ],
    entryComponents: [OrderListComponent, OrderListUpdateComponent, OrderListDeleteDialogComponent, OrderListDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeliveryOrderListModule {}
