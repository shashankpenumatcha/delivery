import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeliverySharedModule } from 'app/shared';
import { ORDERS_ROUTE, OrdersComponent } from './';

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild([ORDERS_ROUTE])],
    declarations: [OrdersComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrdersModule {}
