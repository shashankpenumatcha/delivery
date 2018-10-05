/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DeliveryTestModule } from '../../../test.module';
import { OrderListUpdateComponent } from 'app/entities/order-list/order-list-update.component';
import { OrderListService } from 'app/entities/order-list/order-list.service';
import { OrderList } from 'app/shared/model/order-list.model';

describe('Component Tests', () => {
    describe('OrderList Management Update Component', () => {
        let comp: OrderListUpdateComponent;
        let fixture: ComponentFixture<OrderListUpdateComponent>;
        let service: OrderListService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [OrderListUpdateComponent]
            })
                .overrideTemplate(OrderListUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrderListUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderListService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrderList(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orderList = entity;
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
                    const entity = new OrderList();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orderList = entity;
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
