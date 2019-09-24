import { Component, OnInit, AfterViewInit ,OnDestroy} from '@angular/core';

import { HttpClient, HttpResponse } from '@angular/common/http';
import { IProduct, Product } from 'app/shared/model//product.model';
import { UserCartService } from 'app/shared/service/userCart.service';
import { ICart, Cart } from 'app/shared/model/cart.model';
import { MessagingService } from 'app/shared/service/messaging.service';
import { Subscription } from 'rxjs';

@Component({
    selector: 'jhi-listing',
    templateUrl: './listing.component.html',
    styleUrls: ['listing.css']
})
export class ListingComponent implements AfterViewInit, OnDestroy {
    _products?: IProduct[];
    products?: Product[];
    subscribed?: boolean;
    loading = true;
    cartLoading = false;
    dataSubscription:Subscription;
    dataSubscriptionOne:Subscription;
    loadingSubscription:Subscription;
    constructor(private http: HttpClient, private cartService: UserCartService, private messagingService: MessagingService) {}

    ngOnDestroy(){
        this.kill(this.loadingSubscription);
        this.kill(this.dataSubscriptionOne);
        this.kill(this.dataSubscription);
    }

    valid(o:any){
        if(o==undefined||o==null){
            return false
        }
        return true
    }

    kill(s:Subscription){
        if(this.valid(s)){
            s.unsubscribe();
        }
    }
    ngAfterViewInit() {
        /*         this.http.get('api/dashboard/report').subscribe(res => {});
 */ this.loadingSubscription=this.cartService.loading.subscribe(l => {
            this.cartLoading = l;
        });

      this.dataSubscription=  this.cartService.data.subscribe(cart => {
            if (cart === undefined || cart === null) {
                this.cartService.loadCart().subscribe(res => {
                    this.getProducts().subscribe(
                        (response: HttpResponse<IProduct[]>) => {
                            if (response.body !== undefined && response.body.length > 0) {
                                this.setProducts(response.body);
                                this.loading = false;
                                if (res !== null && res !== undefined) {
                                    this.cartService.setCart(res);
                                }
                                this.setInCartFromCart(res, this.products);
                            }
                        },
                        (error: any) => {
                            this.loading = false;
                        }
                    );
                });
            } else {
                if (this.products === undefined) {
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
                    this.setInCartFromCart(this.cartService.getCart(), this.products);
                }
            }
        });
    }

    passCart($event) {
        if (!this.subscribed) {
            this.cartService.setCart($event);
            this.setInCartFromCart(this.cartService.getCart(), this.products);
          this.dataSubscriptionOne=  this.cartService.data.subscribe(c => {
                this.subscribed = true;
                this.setInCartFromCart(this.cartService.getCart(), this.products);
            });
        }
    }

    setProducts(products: IProduct[]) {
        this.products = products;
    }

    getProducts() {
        console.log(11111111111111111111111111111111111)
        return this.http.get<IProduct[]>('api/activeProducts', { observe: 'response' });
    }

    setInCartFromCart(cart: Cart, products: Product[]) {
        if (cart !== undefined && cart !== null) {
            this.products = products.map(product => {
                let found = false;
                cart.cartItems.forEach(cartItem => {
                    if (product.id === cartItem.product.id) {
                        product.inCart = cartItem.quantity;
                        found = true;
                    }
                });
                if (!found) {
                    product.inCart = 0;
                }
                return product;
            });
        } else {
            this.products = products.map(product => {
                product.inCart = 0;
                return product;
            });
        }
    }
}
