import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeliverySharedModule } from 'app/shared';
import { USER_CART_ROUTE, UserCartComponent } from './';

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild([USER_CART_ROUTE])],
    declarations: [UserCartComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [UserCartComponent]
})
export class UserCartModule {}
