import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeliverySharedModule } from 'app/shared';
import { MY_ACCCOUNT_ROUTE, MyAccountComponent, AddressPipe } from './';

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild([MY_ACCCOUNT_ROUTE])],
    declarations: [MyAccountComponent, AddressPipe],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyAccountModule {}
