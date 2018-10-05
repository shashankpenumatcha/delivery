/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DeliveryTestModule } from '../../../test.module';
import { OrderStatusComponent } from 'app/entities/order-status/order-status.component';
import { OrderStatusService } from 'app/entities/order-status/order-status.service';
import { OrderStatus } from 'app/shared/model/order-status.model';

describe('Component Tests', () => {
    describe('OrderStatus Management Component', () => {
        let comp: OrderStatusComponent;
        let fixture: ComponentFixture<OrderStatusComponent>;
        let service: OrderStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [OrderStatusComponent],
                providers: []
            })
                .overrideTemplate(OrderStatusComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrderStatusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderStatusService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new OrderStatus(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.orderStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
