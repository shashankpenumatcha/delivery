import { Component, AfterViewInit, Input } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Product } from '../model/product.model';

@Component({
    selector: 'jhi-add-to-cart',
    templateUrl: './add-to-cart.component.html'
})
export class AddToCartComponent implements AfterViewInit {
    @Input() product: Product;
    quantity = 0;

    constructor(private http: HttpClient) {}

    add() {
        this.http
            .post<any>(
                '/api/addToCart',
                { productId: this.product.id, quantity: this.quantity === 0 ? this.product.minimumQuantity : this.quantity },
                { observe: 'response' }
            )
            .subscribe(
                () => {
                    alert('added');
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
