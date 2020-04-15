export interface Employee {
  id: number;
  name: string;
  isActive: boolean;
  department: Department;
}

export interface Department {
  id: number;
  name: string;
}

export interface User {
  username: string;
  password: string;
}
