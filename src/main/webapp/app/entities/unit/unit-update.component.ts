import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from './unit.service';

@Component({
    selector: 'jhi-unit-update',
    templateUrl: './unit-update.component.html'
})
export class UnitUpdateComponent implements OnInit {
    private _unit: IUnit;
    isSaving: boolean;

    constructor(private unitService: UnitService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ unit }) => {
            this.unit = unit;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.unit.id !== undefined) {
            this.subscribeToSaveResponse(this.unitService.update(this.unit));
        } else {
            this.subscribeToSaveResponse(this.unitService.create(this.unit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUnit>>) {
        result.subscribe((res: HttpResponse<IUnit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get unit() {
        return this._unit;
    }

    set unit(unit: IUnit) {
        this._unit = unit;
    }
}
