import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UserCartModule } from '../user-cart/user-cart.module';
import { DeliverySharedModule } from 'app/shared';
import { LISTING_ROUTE, ListingComponent } from './';

@NgModule({
    imports: [DeliverySharedModule, UserCartModule, RouterModule.forChild([LISTING_ROUTE])],
    declarations: [ListingComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeliveryListingModule {}
