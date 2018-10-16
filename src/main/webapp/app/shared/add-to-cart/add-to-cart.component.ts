import { Component, AfterViewInit, Input, Output, EventEmitter } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Product } from '../model/product.model';
import { ICart } from '../model/cart.model';
import { UserCartService } from 'app/shared/service/userCart.service';

@Component({
    selector: 'jhi-add-to-cart',
    templateUrl: './add-to-cart.component.html'
})
export class AddToCartComponent implements AfterViewInit {
    @Input() product: Product;
    @Output() updateList = new EventEmitter();

    quantity = 0;

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
                        this.updateList.emit(this.userCartService.getCart());
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
