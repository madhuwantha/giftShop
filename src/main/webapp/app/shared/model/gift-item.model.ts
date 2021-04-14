import { IUser } from 'app/shared/model/user.model';
import { ICategory } from 'app/shared/model/category.model';
import { ICart } from 'app/shared/model/cart.model';
import { IGiftOrder } from 'app/shared/model/gift-order.model';

export interface IGiftItem {
  id?: number;
  giftName?: string | null;
  descripption?: string | null;
  unitPrice?: number | null;
  avalibleQuantity?: number | null;
  user?: IUser | null;
  category?: ICategory | null;
  carts?: ICart[] | null;
  orders?: IGiftOrder[] | null;
}

export const defaultValue: Readonly<IGiftItem> = {};
