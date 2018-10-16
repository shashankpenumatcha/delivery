import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { IProduct, Product } from 'app/shared/model//product.model';
import { UserCartService } from 'app/shared/service/userCart.service';
import { ICart, Cart } from 'app/shared/model/cart.model';
import { Observable } from 'rxjs';

@Component({
    selector: 'jhi-listing',
    templateUrl: './listing.component.html',
    styleUrls: ['listing.css'],
    providers: [UserCartService]
})
export class ListingComponent implements OnInit {
    _products?: IProduct[];
    products?: Product[];

    loading = true;
    constructor(private http: HttpClient, private cartService: UserCartService) {}
    ngOnInit() {
        this.cartService.loadCart().subscribe((res: ICart) => {
            if (res !== undefined && res !== null) {
                this.cartService.setCart(res);
                this.getProducts().subscribe(
                    (response: HttpResponse<IProduct[]>) => {
                        if (response.body !== undefined && response.body.length > 0) {
                            this.setProducts(response.body);
                            this.loading = false;
                            this.setInCartFromCart(this.cartService.getCart(), this.products);
                        }
                    },
                    (error: any) => {
                        this.loading = false;
                    }
                );
            } else {
                this.getProducts().subscribe(
                    (response: HttpResponse<IProduct[]>) => {
                        if (response.body !== undefined && response.body.length > 0) {
                            this.setProducts(response.body);
                            this.loading = false;
                        }
                    },
                    (error: any) => {
                        this.loading = false;
                    }
                );
            }
        });
    }

    setProducts(products: IProduct[]) {
        this.products = products;
    }

    getProducts() {
        return this.http.get<IProduct[]>('api/activeProducts', { observe: 'response' });
    }

    setInCartFromCart(cart: Cart, products: Product[]) {
        this.products = products.map(product => {
            cart.cartItems.forEach(cartItem => {
                if (product.id === cartItem.product.id) {
                    product.inCart = cartItem.quantity;
                }
            });
            return product;
        });
    }

    updateList($event: any) {
        this.setInCartFromCart($event, this.products);
    }
}
