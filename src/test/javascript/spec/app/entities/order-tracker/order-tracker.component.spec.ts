/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DeliveryTestModule } from '../../../test.module';
import { OrderTrackerComponent } from 'app/entities/order-tracker/order-tracker.component';
import { OrderTrackerService } from 'app/entities/order-tracker/order-tracker.service';
import { OrderTracker } from 'app/shared/model/order-tracker.model';

describe('Component Tests', () => {
    describe('OrderTracker Management Component', () => {
        let comp: OrderTrackerComponent;
        let fixture: ComponentFixture<OrderTrackerComponent>;
        let service: OrderTrackerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [OrderTrackerComponent],
                providers: []
            })
                .overrideTemplate(OrderTrackerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrderTrackerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderTrackerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new OrderTracker(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.orderTrackers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
