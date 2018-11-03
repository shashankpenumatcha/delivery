import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { DeliverySharedModule } from 'app/shared';
import { USER_ORDERS_ROUTE, UserOrdersComponent } from './';
import { UserCartModule } from '../user-cart/user-cart.module';

@NgModule({
    imports: [DeliverySharedModule, UserCartModule, RouterModule.forChild([USER_ORDERS_ROUTE])],
    declarations: [UserOrdersComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserOrdersModule {}
