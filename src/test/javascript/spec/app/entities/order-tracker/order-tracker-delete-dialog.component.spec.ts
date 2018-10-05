/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DeliveryTestModule } from '../../../test.module';
import { OrderTrackerDeleteDialogComponent } from 'app/entities/order-tracker/order-tracker-delete-dialog.component';
import { OrderTrackerService } from 'app/entities/order-tracker/order-tracker.service';

describe('Component Tests', () => {
    describe('OrderTracker Management Delete Component', () => {
        let comp: OrderTrackerDeleteDialogComponent;
        let fixture: ComponentFixture<OrderTrackerDeleteDialogComponent>;
        let service: OrderTrackerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [OrderTrackerDeleteDialogComponent]
            })
                .overrideTemplate(OrderTrackerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrderTrackerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderTrackerService);
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
