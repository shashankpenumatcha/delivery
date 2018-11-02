import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DeliverySharedModule } from 'app/shared';
import { ORDER_TRACKER_ROUTE, OrderTrackerComponent } from '.';

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild([ORDER_TRACKER_ROUTE])],
    declarations: [OrderTrackerComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrderTrackerModule {}
