import { IUser } from 'app/shared/model/user.model';
import { IGiftItem } from 'app/shared/model/gift-item.model';

export interface IGiftOrder {
  id?: number;
  descripption?: string | null;
  user?: IUser | null;
  giftItems?: IGiftItem[] | null;
}

export const defaultValue: Readonly<IGiftOrder> = {};
