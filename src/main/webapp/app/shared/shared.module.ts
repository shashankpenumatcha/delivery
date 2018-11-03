import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { DeliverySharedLibsModule, DeliverySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { AddToCartComponent } from './add-to-cart/add-to-cart.component';
import { AddAddressComponent } from './add-address/add-address.component';

@NgModule({
    imports: [DeliverySharedLibsModule, DeliverySharedCommonModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, AddToCartComponent, AddAddressComponent],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent, AddAddressComponent],
    exports: [DeliverySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, AddToCartComponent, AddAddressComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class DeliverySharedModule {}
