import { IGiftItem } from 'app/shared/model/gift-item.model';

export interface ICategory {
  id?: number;
  categoryName?: string | null;
  giftItems?: IGiftItem[] | null;
}

export const defaultValue: Readonly<ICategory> = {};
