import { IUnit } from 'app/shared/model//unit.model';
import { IInventoryLog } from 'app/shared/model//inventory-log.model';
import { ICartItems } from 'app/shared/model//cart-items.model';
import { IOrderItems } from 'app/shared/model//order-items.model';

export interface IProduct {
    id?: number;
    name?: string;
    minimumQuantity?: number;
    pricePerUnit?: number;
    active?: boolean;
    quantity?: number;
    unit?: IUnit;
    inventoryLogs?: IInventoryLog[];
    cartItems?: ICartItems[];
    orderItems?: IOrderItems[];
}

export class Product implements IProduct {
    constructor(
        public id?: number,
        public name?: string,
        public minimumQuantity?: number,
        public pricePerUnit?: number,
        public active?: boolean,
        public quantity?: number,
        public unit?: IUnit,
        public inventoryLogs?: IInventoryLog[],
        public cartItems?: ICartItems[],
        public orderItems?: IOrderItems[],
        public inCart?: number
    ) {
        this.active = this.active || false;
    }
}
