import { IProduct } from 'app/shared/model//product.model';
import { IOrderList } from 'app/shared/model//order-list.model';

export interface IOrderItems {
    id?: number;
    product?: IProduct;
    orderList?: IOrderList;
}

export class OrderItems implements IOrderItems {
    constructor(public id?: number, public product?: IProduct, public orderList?: IOrderList) {}
}
