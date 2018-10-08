import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';

import { MenuComponent } from 'app/layouts/menu/menu.component';

export const menuRoute: Route = {
    path: '',
    component: MenuComponent,
    outlet: 'menu',
    data: {
        authorities: ['ROLE_USER']
    }
};
