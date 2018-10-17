import { Component, AfterViewInit, Input, Output, EventEmitter } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Product } from '../model/product.model';
import { ICart } from '../model/cart.model';
import { UserCartService } from 'app/shared/service/userCart.service';
import { ICartItems, CartItems } from '../model/cart-items.model';

@Component({
    selector: 'jhi-add-to-cart',
    templateUrl: './add-to-cart.component.html'
})
export class AddToCartComponent implements AfterViewInit {
    @Input() product: Product;
    @Output() passCart = new EventEmitter();
    quantity = 0;
    cartItems: ICartItems[];

    constructor(private http: HttpClient, private userCartService: UserCartService) {}

    add() {
        this.http
            .post<any>(
                '/api/addToCart',
                { productId: this.product.id, quantity: this.product.inCart > 0 ? 1 : this.product.minimumQuantity },
                { observe: 'response' }
            )
            .subscribe(
                (res: HttpResponse<ICart>) => {
                    if (res.body !== undefined) {
                        this.userCartService.setCart(res.body);
                        this.passCart.emit(this.userCartService.getCart());
                    }
                },
                () => {
                    // handle error
                }
            );
    }

    subtract() {
        this.cartItems = this.userCartService.getCart().cartItems.filter(ci => {
            return ci.product.id === this.product.id;
        });
        this.http
            .put<any>('/api/updateCart', { id: this.cartItems[0].id, quantity: this.product.inCart - 1 }, { observe: 'response' })
            .subscribe(
                (res: HttpResponse<ICart>) => {
                    if (res.body !== undefined) {
                        if (this.product.inCart === this.product.minimumQuantity) {
                            this.product.inCart = 0;
                        }
                        this.userCartService.setCart(res.body);
                    }
                },
                () => {
                    // handle error
                }
            );
    }

    ngAfterViewInit() {
        this.quantity = this.product.minimumQuantity;
    }
}
