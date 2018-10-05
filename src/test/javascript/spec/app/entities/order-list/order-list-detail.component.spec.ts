/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DeliveryTestModule } from '../../../test.module';
import { OrderListDetailComponent } from 'app/entities/order-list/order-list-detail.component';
import { OrderList } from 'app/shared/model/order-list.model';

describe('Component Tests', () => {
    describe('OrderList Management Detail Component', () => {
        let comp: OrderListDetailComponent;
        let fixture: ComponentFixture<OrderListDetailComponent>;
        const route = ({ data: of({ orderList: new OrderList(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [OrderListDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrderListDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrderListDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.orderList).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
