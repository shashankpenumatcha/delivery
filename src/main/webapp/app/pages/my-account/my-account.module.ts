import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeliverySharedModule } from 'app/shared';
import { MY_ACCCOUNT_ROUTE, MyAccountComponent } from './';

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild([MY_ACCCOUNT_ROUTE])],
    declarations: [MyAccountComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyAccountModule {}
