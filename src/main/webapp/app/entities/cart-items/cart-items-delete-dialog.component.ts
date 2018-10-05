import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICartItems } from 'app/shared/model/cart-items.model';
import { CartItemsService } from './cart-items.service';

@Component({
    selector: 'jhi-cart-items-delete-dialog',
    templateUrl: './cart-items-delete-dialog.component.html'
})
export class CartItemsDeleteDialogComponent {
    cartItems: ICartItems;

    constructor(private cartItemsService: CartItemsService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cartItemsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cartItemsListModification',
                content: 'Deleted an cartItems'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cart-items-delete-popup',
    template: ''
})
export class CartItemsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cartItems }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CartItemsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.cartItems = cartItems;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
