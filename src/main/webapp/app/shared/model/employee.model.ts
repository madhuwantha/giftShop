import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IEmployee {
  id?: number;
  name?: string | null;
  age?: number | null;
  basicSallary?: number | null;
  employmentDate?: string | null;
  numberOfDependants?: number | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IEmployee> = {};
