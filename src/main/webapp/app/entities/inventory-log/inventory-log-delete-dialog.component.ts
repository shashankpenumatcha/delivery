import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryLog } from 'app/shared/model/inventory-log.model';
import { InventoryLogService } from './inventory-log.service';

@Component({
    selector: 'jhi-inventory-log-delete-dialog',
    templateUrl: './inventory-log-delete-dialog.component.html'
})
export class InventoryLogDeleteDialogComponent {
    inventoryLog: IInventoryLog;

    constructor(
        private inventoryLogService: InventoryLogService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inventoryLogService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'inventoryLogListModification',
                content: 'Deleted an inventoryLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inventory-log-delete-popup',
    template: ''
})
export class InventoryLogDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ inventoryLog }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InventoryLogDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.inventoryLog = inventoryLog;
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
