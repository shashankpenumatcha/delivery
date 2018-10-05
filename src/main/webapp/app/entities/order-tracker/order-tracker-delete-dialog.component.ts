import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderTracker } from 'app/shared/model/order-tracker.model';
import { OrderTrackerService } from './order-tracker.service';

@Component({
    selector: 'jhi-order-tracker-delete-dialog',
    templateUrl: './order-tracker-delete-dialog.component.html'
})
export class OrderTrackerDeleteDialogComponent {
    orderTracker: IOrderTracker;

    constructor(
        private orderTrackerService: OrderTrackerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderTrackerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'orderTrackerListModification',
                content: 'Deleted an orderTracker'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-tracker-delete-popup',
    template: ''
})
export class OrderTrackerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orderTracker }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OrderTrackerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.orderTracker = orderTracker;
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
