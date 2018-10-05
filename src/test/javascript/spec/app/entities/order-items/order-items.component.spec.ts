/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DeliveryTestModule } from '../../../test.module';
import { OrderItemsComponent } from 'app/entities/order-items/order-items.component';
import { OrderItemsService } from 'app/entities/order-items/order-items.service';
import { OrderItems } from 'app/shared/model/order-items.model';

describe('Component Tests', () => {
    describe('OrderItems Management Component', () => {
        let comp: OrderItemsComponent;
        let fixture: ComponentFixture<OrderItemsComponent>;
        let service: OrderItemsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [OrderItemsComponent],
                providers: []
            })
                .overrideTemplate(OrderItemsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrderItemsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderItemsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new OrderItems(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.orderItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
