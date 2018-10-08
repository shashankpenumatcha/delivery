import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute, menuRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { HomeComponent } from 'app/home';
import { ListingComponent } from 'app/pages/listing';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute, menuRoute];

@NgModule({
    imports: [
        RouterModule.forRoot(
            [
                ...LAYOUT_ROUTES,
                {
                    path: 'admin',
                    loadChildren: './admin/admin.module#DeliveryAdminModule'
                }
            ],
            { useHash: true, enableTracing: DEBUG_INFO_ENABLED }
        )
    ],
    exports: [RouterModule]
})
export class DeliveryAppRoutingModule {}
