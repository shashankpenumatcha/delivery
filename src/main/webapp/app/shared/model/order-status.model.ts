import { IOrderTracker } from 'app/shared/model//order-tracker.model';
import { IOrderList } from 'app/shared/model//order-list.model';

export interface IOrderStatus {
    id?: number;
    name?: string;
    orderTrackers?: IOrderTracker[];
    orderLists?: IOrderList[];
}

export class OrderStatus implements IOrderStatus {
    constructor(public id?: number, public name?: string, public orderTrackers?: IOrderTracker[], public orderLists?: IOrderList[]) {}
}
