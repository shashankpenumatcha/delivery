import { NgModule } from '@angular/core';

import { DeliverySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [DeliverySharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [DeliverySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class DeliverySharedCommonModule {}
