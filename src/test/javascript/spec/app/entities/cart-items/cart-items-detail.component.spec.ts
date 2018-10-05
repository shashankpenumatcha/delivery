/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DeliveryTestModule } from '../../../test.module';
import { CartItemsDetailComponent } from 'app/entities/cart-items/cart-items-detail.component';
import { CartItems } from 'app/shared/model/cart-items.model';

describe('Component Tests', () => {
    describe('CartItems Management Detail Component', () => {
        let comp: CartItemsDetailComponent;
        let fixture: ComponentFixture<CartItemsDetailComponent>;
        const route = ({ data: of({ cartItems: new CartItems(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DeliveryTestModule],
                declarations: [CartItemsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CartItemsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CartItemsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cartItems).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
