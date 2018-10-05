/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DeliveryTestModule } from '../../../test.module';
import { OrderListComponent } from 'app/entities/order-list/order-list.component';
import { OrderListService } from 'app/entities/order-list/order-list.service';
import { OrderList } from 'app/shared/model/order-list.model';

describe('Component Tests', () => {
    describe('OrderList Management Component', () => {
        let comp: OrderListComponent;
        let fixture: ComponentFixture<OrderListComponent>;
        let service: OrderListService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [OrderListComponent],
                providers: []
            })
                .overrideTemplate(OrderListComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrderListComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderListService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new OrderList(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.orderLists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
