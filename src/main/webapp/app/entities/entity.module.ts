import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { DeliveryOrderTrackerModule } from './order-tracker/order-tracker.module';
import { DeliveryUserProfileModule } from './user-profile/user-profile.module';
import { DeliveryProductModule } from './product/product.module';
import { DeliveryUnitModule } from './unit/unit.module';
import { DeliveryOrderStatusModule } from './order-status/order-status.module';
import { DeliveryInventoryLogModule } from './inventory-log/inventory-log.module';
import { DeliveryCartModule } from './cart/cart.module';
import { DeliveryCartItemsModule } from './cart-items/cart-items.module';
import { DeliveryOrderListModule } from './order-list/order-list.module';
import { DeliveryOrderItemsModule } from './order-items/order-items.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        DeliveryOrderTrackerModule,
        DeliveryUserProfileModule,
        DeliveryProductModule,
        DeliveryUnitModule,
        DeliveryOrderStatusModule,
        DeliveryInventoryLogModule,
        DeliveryCartModule,
        DeliveryCartItemsModule,
        DeliveryOrderListModule,
        DeliveryOrderItemsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DeliveryEntityModule {}
