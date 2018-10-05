import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserProfile } from 'app/shared/model/user-profile.model';
import { Principal } from 'app/core';
import { UserProfileService } from './user-profile.service';

@Component({
    selector: 'jhi-user-profile',
    templateUrl: './user-profile.component.html'
})
export class UserProfileComponent implements OnInit, OnDestroy {
    userProfiles: IUserProfile[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userProfileService: UserProfileService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.userProfileService.query().subscribe(
            (res: HttpResponse<IUserProfile[]>) => {
                this.userProfiles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInUserProfiles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IUserProfile) {
        return item.id;
    }

    registerChangeInUserProfiles() {
        this.eventSubscriber = this.eventManager.subscribe('userProfileListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
