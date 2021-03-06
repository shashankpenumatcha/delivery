import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage, LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { DeliverySharedModule } from 'app/shared';
import { DeliveryCoreModule } from 'app/core';
import { DeliveryAppRoutingModule } from './app-routing.module';
import { DeliveryHomeModule } from './home/home.module';
import { DeliveryAccountModule } from './account/account.module';
import { DeliveryEntityModule } from './entities/entity.module';
import * as moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent, MenuComponent } from './layouts';
import { DeliveryListingModule } from 'app/pages/listing';
import { MyAccountModule } from './pages/my-account/my-account.module';
import { UserCartModule } from './pages/user-cart/user-cart.module';
import { UserOrdersModule } from './pages/user-orders/user-orders.module';
import { DashboardModule } from './pages/admin/dashboard/dashboard.module';
import { ProductsModule } from './pages/admin/products';
import { AngularFireMessagingModule } from '@angular/fire/messaging';
import { AngularFireModule } from '@angular/fire';
import {environment } from './shared/environment';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { OrdersModule } from './pages/admin/orders/orders.module';
import { OrderListModule } from './pages/admin/order-list/order-list.module';
import { OrderTrackerModule } from './pages/order-tracker/order-tracker.module';
import { NgxNotificationModule } from 'ngx-notification';
@NgModule({
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        DeliveryAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        DeliverySharedModule,
        DeliveryCoreModule,
        DeliveryHomeModule,
        DeliveryAccountModule,
        DeliveryEntityModule,
        DeliveryListingModule,
        MyAccountModule,
        UserCartModule,
        UserOrdersModule,
        DashboardModule,
        ProductsModule,
        AngularFireMessagingModule,
        OrdersModule,
        OrderListModule,
        OrderTrackerModule,
        NgxNotificationModule,
        AngularFireModule.initializeApp(environment.firebase),
        NgbModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [ JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent, MenuComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
            deps: [LocalStorageService, SessionStorageService]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [Injector]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [JhiEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class DeliveryAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
