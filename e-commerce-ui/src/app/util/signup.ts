export interface SignupBody {
  email: string;
  firstName: string;
  lastName: string;
  username: string;
  password: string;
  role: Role;
}

export enum Role {
  CUSTOMER,
  ADMIN,
  SELLER
}
