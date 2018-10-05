/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DeliveryTestModule } from '../../../test.module';
import { OrderTrackerDetailComponent } from 'app/entities/order-tracker/order-tracker-detail.component';
import { OrderTracker } from 'app/shared/model/order-tracker.model';

describe('Component Tests', () => {
    describe('OrderTracker Management Detail Component', () => {
        let comp: OrderTrackerDetailComponent;
        let fixture: ComponentFixture<OrderTrackerDetailComponent>;
        const route = ({ data: of({ orderTracker: new OrderTracker(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [OrderTrackerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrderTrackerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrderTrackerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.orderTracker).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
