/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DeliveryTestModule } from '../../../test.module';
import { CartItemsComponent } from 'app/entities/cart-items/cart-items.component';
import { CartItemsService } from 'app/entities/cart-items/cart-items.service';
import { CartItems } from 'app/shared/model/cart-items.model';

describe('Component Tests', () => {
    describe('CartItems Management Component', () => {
        let comp: CartItemsComponent;
        let fixture: ComponentFixture<CartItemsComponent>;
        let service: CartItemsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [CartItemsComponent],
                providers: []
            })
                .overrideTemplate(CartItemsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CartItemsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CartItemsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CartItems(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.cartItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
