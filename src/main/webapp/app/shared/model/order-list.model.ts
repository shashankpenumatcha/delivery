import { Moment } from 'moment';
import { IOrderItems } from 'app/shared/model//order-items.model';
import { IOrderTracker } from 'app/shared/model//order-tracker.model';
import { IOrderStatus } from 'app/shared/model//order-status.model';
import { IUserProfile } from 'app/shared/model//user-profile.model';

export interface IOrderList {
    id?: number;
    lastUpdated?: Moment;
    orderItems?: IOrderItems[];
    orderTrackers?: IOrderTracker[];
    orderStatus?: IOrderStatus;
    userProfile?: IUserProfile;
}

export class OrderList implements IOrderList {
    constructor(
        public id?: number,
        public lastUpdated?: Moment,
        public orderItems?: IOrderItems[],
        public orderTrackers?: IOrderTracker[],
        public orderStatus?: IOrderStatus,
        public userProfile?: IUserProfile
    ) {}
}
