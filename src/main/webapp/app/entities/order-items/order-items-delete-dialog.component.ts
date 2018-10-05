import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderItems } from 'app/shared/model/order-items.model';
import { OrderItemsService } from './order-items.service';

@Component({
    selector: 'jhi-order-items-delete-dialog',
    templateUrl: './order-items-delete-dialog.component.html'
})
export class OrderItemsDeleteDialogComponent {
    orderItems: IOrderItems;

    constructor(private orderItemsService: OrderItemsService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderItemsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'orderItemsListModification',
                content: 'Deleted an orderItems'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-items-delete-popup',
    template: ''
})
export class OrderItemsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orderItems }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OrderItemsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.orderItems = orderItems;
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
