import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeliverySharedModule } from 'app/shared';
import {
    UnitComponent,
    UnitDetailComponent,
    UnitUpdateComponent,
    UnitDeletePopupComponent,
    UnitDeleteDialogComponent,
    unitRoute,
    unitPopupRoute
} from './';

const ENTITY_STATES = [...unitRoute, ...unitPopupRoute];

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [UnitComponent, UnitDetailComponent, UnitUpdateComponent, UnitDeleteDialogComponent, UnitDeletePopupComponent],
    entryComponents: [UnitComponent, UnitUpdateComponent, UnitDeleteDialogComponent, UnitDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeliveryUnitModule {}
