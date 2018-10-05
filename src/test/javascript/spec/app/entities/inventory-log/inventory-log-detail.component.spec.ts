/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DeliveryTestModule } from '../../../test.module';
import { InventoryLogDetailComponent } from 'app/entities/inventory-log/inventory-log-detail.component';
import { InventoryLog } from 'app/shared/model/inventory-log.model';

describe('Component Tests', () => {
    describe('InventoryLog Management Detail Component', () => {
        let comp: InventoryLogDetailComponent;
        let fixture: ComponentFixture<InventoryLogDetailComponent>;
        const route = ({ data: of({ inventoryLog: new InventoryLog(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [InventoryLogDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InventoryLogDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InventoryLogDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.inventoryLog).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
