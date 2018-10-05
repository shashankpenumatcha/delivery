/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DeliveryTestModule } from '../../../test.module';
import { OrderListDeleteDialogComponent } from 'app/entities/order-list/order-list-delete-dialog.component';
import { OrderListService } from 'app/entities/order-list/order-list.service';

describe('Component Tests', () => {
    describe('OrderList Management Delete Component', () => {
        let comp: OrderListDeleteDialogComponent;
        let fixture: ComponentFixture<OrderListDeleteDialogComponent>;
        let service: OrderListService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [OrderListDeleteDialogComponent]
            })
                .overrideTemplate(OrderListDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrderListDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderListService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
