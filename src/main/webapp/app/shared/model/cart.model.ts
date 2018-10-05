import { Moment } from 'moment';
import { IUserProfile } from 'app/shared/model//user-profile.model';
import { ICartItems } from 'app/shared/model//cart-items.model';

export interface ICart {
    id?: number;
    lastUpdated?: Moment;
    userProfile?: IUserProfile;
    cartItems?: ICartItems[];
}

export class Cart implements ICart {
    constructor(public id?: number, public lastUpdated?: Moment, public userProfile?: IUserProfile, public cartItems?: ICartItems[]) {}
}
