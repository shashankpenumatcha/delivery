import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeliverySharedModule } from 'app/shared';
import { ORDER_LIST_ROUTE, OrderListComponent } from './';

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild([ORDER_LIST_ROUTE])],
    declarations: [OrderListComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrderListModule {}
