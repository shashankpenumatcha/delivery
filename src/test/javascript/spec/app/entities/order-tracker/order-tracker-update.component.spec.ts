/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DeliveryTestModule } from '../../../test.module';
import { OrderTrackerUpdateComponent } from 'app/entities/order-tracker/order-tracker-update.component';
import { OrderTrackerService } from 'app/entities/order-tracker/order-tracker.service';
import { OrderTracker } from 'app/shared/model/order-tracker.model';

describe('Component Tests', () => {
    describe('OrderTracker Management Update Component', () => {
        let comp: OrderTrackerUpdateComponent;
        let fixture: ComponentFixture<OrderTrackerUpdateComponent>;
        let service: OrderTrackerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [OrderTrackerUpdateComponent]
            })
                .overrideTemplate(OrderTrackerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrderTrackerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderTrackerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrderTracker(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orderTracker = entity;
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
                    const entity = new OrderTracker();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orderTracker = entity;
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
