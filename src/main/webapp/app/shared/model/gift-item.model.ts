import { ICategory } from 'app/shared/model/category.model';
import { ICart } from 'app/shared/model/cart.model';
import { IGiftOrder } from 'app/shared/model/gift-order.model';
import { IImage } from 'app/shared/model/image.model';

export interface IGiftItem {
  id?: number;
  giftName?: string | null;
  descripption?: string | null;
  unitPrice?: number | null;
  avalibleQuantity?: number | null;
  category?: ICategory | null;
  carts?: ICart[] | null;
  orders?: IGiftOrder[] | null;
  image?: any | IImage | null;
}

export const defaultValue: Readonly<IGiftItem> = {};
