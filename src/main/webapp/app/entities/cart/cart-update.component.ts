import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICart } from 'app/shared/model/cart.model';
import { CartService } from './cart.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile';

@Component({
    selector: 'jhi-cart-update',
    templateUrl: './cart-update.component.html'
})
export class CartUpdateComponent implements OnInit {
    private _cart: ICart;
    isSaving: boolean;

    userprofiles: IUserProfile[];
    lastUpdated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private cartService: CartService,
        private userProfileService: UserProfileService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cart }) => {
            this.cart = cart;
        });
        this.userProfileService.query({ filter: 'cart-is-null' }).subscribe(
            (res: HttpResponse<IUserProfile[]>) => {
                if (!this.cart.userProfile || !this.cart.userProfile.id) {
                    this.userprofiles = res.body;
                } else {
                    this.userProfileService.find(this.cart.userProfile.id).subscribe(
                        (subRes: HttpResponse<IUserProfile>) => {
                            this.userprofiles = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.cart.lastUpdated = moment(this.lastUpdated, DATE_TIME_FORMAT);
        if (this.cart.id !== undefined) {
            this.subscribeToSaveResponse(this.cartService.update(this.cart));
        } else {
            this.subscribeToSaveResponse(this.cartService.create(this.cart));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICart>>) {
        result.subscribe((res: HttpResponse<ICart>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserProfileById(index: number, item: IUserProfile) {
        return item.id;
    }
    get cart() {
        return this._cart;
    }

    set cart(cart: ICart) {
        this._cart = cart;
        this.lastUpdated = moment(cart.lastUpdated).format(DATE_TIME_FORMAT);
    }
}
