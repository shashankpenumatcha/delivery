import { Component, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ICart } from 'app/shared/model/cart.model';
import { UserCartService } from 'app/shared/service/userCart.service';
import { CartItems } from 'app/shared/model/cart-items.model';
import { UserAddressService } from '../../shared/service/userAddressService';
import { AddAddressComponent } from '../../shared/add-address/add-address.component';
import { Pipe, PipeTransform } from '@angular/core';
import { IUserAddress, UserAddress } from '../../shared/model/user-address.module';
import { NgbModalRef, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Pipe({
  name: 'address'
})
export class AddressPipe implements PipeTransform {
  transform(value: string): string {
    let trans = value;
    trans = trans.replace(new RegExp(';', 'g'), ', ');
    trans = trans.replace(new RegExp(', -', 'g'), '');

    return trans;
  }
}
@Component({
    selector: 'jhi-user-cart',
    templateUrl: './user-cart.component.html',
    styleUrls: ['user-cart.css']
})
export class UserCartComponent implements AfterViewInit {
    cart: ICart;
    total: number;
    cartLoading = false;
    loading = true;
    userAddresses: IUserAddress[];
    address: number;

    constructor(private http: HttpClient, private router: Router, private cartService: UserCartService,
         private userAddressService: UserAddressService, private modalService: NgbModal) {}
    ngAfterViewInit() {
        this.cartService.loading.subscribe(l => {
            this.cartLoading = l;
        });
        this.loading = true;
        this.http.get('api/getCartForUser').subscribe(
            res => {
                this.loading = false;
                    this.cartService.setCart(res);
                    this.cartService.data.subscribe(cart => {
                        if (cart !== undefined) {
                            this.cart = cart;
                            if (this.cart !== null) {
                                this.setInCartFromCart(this.cart);
                            }
                        }
                    });
            },
            err => {
                this.loading = false;
            }
        );

        this.userAddressService.userAddresses.subscribe( res => {
            this.userAddresses = res;
        });

        this.userAddressService.loadUserAddresses().subscribe(
            res => {
                this.userAddressService.setUserAddresses(res);
            }
        );
    }

    setInCartFromCart(cart: ICart) {
        if (cart !== undefined && cart !== null && cart.cartItems !== undefined && cart.cartItems.length > 0) {
            this.total = 0;
            this.cart.cartItems = this.cart.cartItems.map(ci => {
                this.total += ci.product.pricePerUnit * ci.quantity;
                ci.product.inCart = ci.quantity;
                return ci;
            });
        }
    }

    placeOrder() {
        this.cartService.setLoading(true);
        this.http.post('api/placeOrder?address=' + this.address, {}).subscribe(
            res => {
                // alert('order placed');
                this.cartService.setCart(null);
                this.cartService.setLoading(false);
            },
            err => {
                this.cartService.setLoading(false);
            }
        );
    }

    selectAddress(address: any) {
        this.address = address.id;
        console.log(this.address);
    }

    openAddAddress() {

        const modalRef = this.modalService.open(AddAddressComponent,  { centered: true, size: 'lg' });

    }
}
