import { IGiftItem } from 'app/shared/model/gift-item.model';

export interface ICart {
  id?: number;
  descripption?: string | null;
  giftItems?: IGiftItem[] | null;
}

export const defaultValue: Readonly<ICart> = {};
