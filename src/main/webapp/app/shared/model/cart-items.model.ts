import { IProduct } from 'app/shared/model//product.model';
import { ICart } from 'app/shared/model//cart.model';

export interface ICartItems {
    id?: number;
    product?: IProduct;
    cart?: ICart;
}

export class CartItems implements ICartItems {
    constructor(public id?: number, public product?: IProduct, public cart?: ICart) {}
}
