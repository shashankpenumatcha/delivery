/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DeliveryTestModule } from '../../../test.module';
import { InventoryLogUpdateComponent } from 'app/entities/inventory-log/inventory-log-update.component';
import { InventoryLogService } from 'app/entities/inventory-log/inventory-log.service';
import { InventoryLog } from 'app/shared/model/inventory-log.model';

describe('Component Tests', () => {
    describe('InventoryLog Management Update Component', () => {
        let comp: InventoryLogUpdateComponent;
        let fixture: ComponentFixture<InventoryLogUpdateComponent>;
        let service: InventoryLogService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [InventoryLogUpdateComponent]
            })
                .overrideTemplate(InventoryLogUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InventoryLogUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventoryLogService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InventoryLog(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.inventoryLog = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InventoryLog();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.inventoryLog = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
