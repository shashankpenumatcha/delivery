import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from './user-profile.service';
import { UserProfileComponent } from './user-profile.component';
import { UserProfileDetailComponent } from './user-profile-detail.component';
import { UserProfileUpdateComponent } from './user-profile-update.component';
import { UserProfileDeletePopupComponent } from './user-profile-delete-dialog.component';
import { IUserProfile } from 'app/shared/model/user-profile.model';

@Injectable({ providedIn: 'root' })
export class UserProfileResolve implements Resolve<IUserProfile> {
    constructor(private service: UserProfileService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((userProfile: HttpResponse<UserProfile>) => userProfile.body));
        }
        return of(new UserProfile());
    }
}

export const userProfileRoute: Routes = [
    {
        path: 'user-profile',
        component: UserProfileComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserProfiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-profile/:id/view',
        component: UserProfileDetailComponent,
        resolve: {
            userProfile: UserProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserProfiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-profile/new',
        component: UserProfileUpdateComponent,
        resolve: {
            userProfile: UserProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserProfiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'user-profile/:id/edit',
        component: UserProfileUpdateComponent,
        resolve: {
            userProfile: UserProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserProfiles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userProfilePopupRoute: Routes = [
    {
        path: 'user-profile/:id/delete',
        component: UserProfileDeletePopupComponent,
        resolve: {
            userProfile: UserProfileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserProfiles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
