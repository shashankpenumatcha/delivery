import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DeliverySharedModule } from 'app/shared';
import {
    InventoryLogComponent,
    InventoryLogDetailComponent,
    InventoryLogUpdateComponent,
    InventoryLogDeletePopupComponent,
    InventoryLogDeleteDialogComponent,
    inventoryLogRoute,
    inventoryLogPopupRoute
} from './';

const ENTITY_STATES = [...inventoryLogRoute, ...inventoryLogPopupRoute];

@NgModule({
    imports: [DeliverySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InventoryLogComponent,
        InventoryLogDetailComponent,
        InventoryLogUpdateComponent,
        InventoryLogDeleteDialogComponent,
        InventoryLogDeletePopupComponent
    ],
    entryComponents: [
        InventoryLogComponent,
        InventoryLogUpdateComponent,
        InventoryLogDeleteDialogComponent,
        InventoryLogDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeliveryInventoryLogModule {}
