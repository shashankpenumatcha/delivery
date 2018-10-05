/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DeliveryTestModule } from '../../../test.module';
import { OrderItemsDeleteDialogComponent } from 'app/entities/order-items/order-items-delete-dialog.component';
import { OrderItemsService } from 'app/entities/order-items/order-items.service';

describe('Component Tests', () => {
    describe('OrderItems Management Delete Component', () => {
        let comp: OrderItemsDeleteDialogComponent;
        let fixture: ComponentFixture<OrderItemsDeleteDialogComponent>;
        let service: OrderItemsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [OrderItemsDeleteDialogComponent]
            })
                .overrideTemplate(OrderItemsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrderItemsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderItemsService);
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
