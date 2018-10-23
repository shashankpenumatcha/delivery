import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeliverySharedModule } from 'app/shared';
import { DASHBOARD_ROUTE, DashboardComponent } from '.';

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild([DASHBOARD_ROUTE])],
    declarations: [DashboardComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashboardModule {}
