import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeliverySharedModule } from 'app/shared';
import {
    OrderTrackerComponent,
    OrderTrackerDetailComponent,
    OrderTrackerUpdateComponent,
    OrderTrackerDeletePopupComponent,
    OrderTrackerDeleteDialogComponent,
    orderTrackerRoute,
    orderTrackerPopupRoute
} from './';

const ENTITY_STATES = [...orderTrackerRoute, ...orderTrackerPopupRoute];

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OrderTrackerComponent,
        OrderTrackerDetailComponent,
        OrderTrackerUpdateComponent,
        OrderTrackerDeleteDialogComponent,
        OrderTrackerDeletePopupComponent
    ],
    entryComponents: [
        OrderTrackerComponent,
        OrderTrackerUpdateComponent,
        OrderTrackerDeleteDialogComponent,
        OrderTrackerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeliveryOrderTrackerModule {}
