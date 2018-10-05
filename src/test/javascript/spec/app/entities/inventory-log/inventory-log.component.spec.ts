/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DeliveryTestModule } from '../../../test.module';
import { InventoryLogComponent } from 'app/entities/inventory-log/inventory-log.component';
import { InventoryLogService } from 'app/entities/inventory-log/inventory-log.service';
import { InventoryLog } from 'app/shared/model/inventory-log.model';

describe('Component Tests', () => {
    describe('InventoryLog Management Component', () => {
        let comp: InventoryLogComponent;
        let fixture: ComponentFixture<InventoryLogComponent>;
        let service: InventoryLogService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [InventoryLogComponent],
                providers: []
            })
                .overrideTemplate(InventoryLogComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InventoryLogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventoryLogService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new InventoryLog(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.inventoryLogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
