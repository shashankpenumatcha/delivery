import { Moment } from 'moment';
import { IOrderList } from 'app/shared/model//order-list.model';
import { IOrderStatus } from 'app/shared/model//order-status.model';
import { IUserProfile } from 'app/shared/model//user-profile.model';

export interface IOrderTracker {
    id?: number;
    dateTime?: Moment;
    orderList?: IOrderList;
    orderStatus?: IOrderStatus;
    userProfile?: IUserProfile;
}

export class OrderTracker implements IOrderTracker {
    constructor(
        public id?: number,
        public dateTime?: Moment,
        public orderList?: IOrderList,
        public orderStatus?: IOrderStatus,
        public userProfile?: IUserProfile
    ) {}
}
