import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryLog } from 'app/shared/model/inventory-log.model';

@Component({
    selector: 'jhi-inventory-log-detail',
    templateUrl: './inventory-log-detail.component.html'
})
export class InventoryLogDetailComponent implements OnInit {
    inventoryLog: IInventoryLog;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ inventoryLog }) => {
            this.inventoryLog = inventoryLog;
        });
    }

    previousState() {
        window.history.back();
    }
}
