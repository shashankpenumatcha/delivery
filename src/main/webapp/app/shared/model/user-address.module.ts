
import { IUserProfile } from 'app/shared/model//user-profile.model';

export interface IUserAddress {
    id?: number;
    address?: string;
    userProfile?: IUserProfile;
}

export class UserAddress implements IUserAddress {
    constructor(
        public id?: number,
        public address?: string,
        public userProfile?: IUserProfile
    ) {}
}
