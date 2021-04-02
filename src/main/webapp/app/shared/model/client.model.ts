import { IUser } from 'app/shared/model/user.model';

export interface IClient {
  id?: number;
  name?: string | null;
  addressLineOne?: string | null;
  addressLineTwo?: string | null;
  city?: string | null;
  postalCode?: number | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IClient> = {};
