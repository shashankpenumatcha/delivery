import { Moment } from 'moment';
import { IProduct } from 'app/shared/model//product.model';
import { IUserProfile } from 'app/shared/model//user-profile.model';

export interface IInventoryLog {
    id?: number;
    date?: Moment;
    added?: boolean;
    removed?: boolean;
    quantity?: number;
    product?: IProduct;
    userProfile?: IUserProfile;
}

export class InventoryLog implements IInventoryLog {
    constructor(
        public id?: number,
        public date?: Moment,
        public added?: boolean,
        public removed?: boolean,
        public quantity?: number,
        public product?: IProduct,
        public userProfile?: IUserProfile
    ) {
        this.added = this.added || false;
        this.removed = this.removed || false;
    }
}
