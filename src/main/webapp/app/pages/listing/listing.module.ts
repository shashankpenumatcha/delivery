import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeliverySharedModule, AddToCartComponent } from 'app/shared';
import { LISTING_ROUTE, ListingComponent } from './';

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild([LISTING_ROUTE])],
    declarations: [ListingComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeliveryListingModule {}
