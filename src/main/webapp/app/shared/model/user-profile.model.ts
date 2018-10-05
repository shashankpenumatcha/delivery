import { IUser } from 'app/core/user/user.model';
import { IOrderList } from 'app/shared/model//order-list.model';
import { IInventoryLog } from 'app/shared/model//inventory-log.model';
import { IOrderTracker } from 'app/shared/model//order-tracker.model';
import { ICart } from 'app/shared/model//cart.model';

export interface IUserProfile {
    id?: number;
    customer?: boolean;
    user?: IUser;
    orderLists?: IOrderList[];
    inventoryLogs?: IInventoryLog[];
    orderTrackers?: IOrderTracker[];
    cart?: ICart;
}

export class UserProfile implements IUserProfile {
    constructor(
        public id?: number,
        public customer?: boolean,
        public user?: IUser,
        public orderLists?: IOrderList[],
        public inventoryLogs?: IInventoryLog[],
        public orderTrackers?: IOrderTracker[],
        public cart?: ICart
    ) {
        this.customer = this.customer || false;
    }
}
