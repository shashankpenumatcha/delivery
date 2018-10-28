import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeliverySharedModule } from 'app/shared';
import { PRODUCTS_ROUTE, ProductsComponent } from './';

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild([PRODUCTS_ROUTE])],
    declarations: [ProductsComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProductsModule {}
