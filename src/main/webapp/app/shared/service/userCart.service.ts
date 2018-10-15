import { Injectable } from '@angular/core';
import { ICart } from 'app/shared/model//cart.model';
import { IProduct } from 'app/shared/model//product.model';

@Injectable({
    providedIn: 'root'
})
export class UserCartService {
    private cart?: ICart;
    constructor() {}
}
