/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { DeliveryTestModule } from '../../../test.module';
import { CartItemsUpdateComponent } from 'app/entities/cart-items/cart-items-update.component';
import { CartItemsService } from 'app/entities/cart-items/cart-items.service';
import { CartItems } from 'app/shared/model/cart-items.model';

describe('Component Tests', () => {
    describe('CartItems Management Update Component', () => {
        let comp: CartItemsUpdateComponent;
        let fixture: ComponentFixture<CartItemsUpdateComponent>;
        let service: CartItemsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [CartItemsUpdateComponent]
            })
                .overrideTemplate(CartItemsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CartItemsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CartItemsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CartItems(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cartItems = entity;
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
                    const entity = new CartItems();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cartItems = entity;
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
