import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {EmployeeService} from '../../services/employee.service';

export interface Department {
  id: number;
  name: string;
}
export interface Employee {
  id: number;
  name: string;
  isActive: boolean;
  department: Department;
}

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit, OnChanges {

  @Input()
  id;
  @Input()
  action;
  @Output()
  closeViewAction = new EventEmitter<string>();


  employee: any;

  constructor(private employeeService: EmployeeService) { }

  ngOnInit() {

  }

  ngOnChanges(changes: SimpleChanges): void {
    this.getEmployee();
  }

  getEmployee() {
    this.employeeService.getEmployeeById(this.id)
        .subscribe( result => {
          this.employee = result;
        });
  }

  close() {
    this.action = 'close-view';
    this.closeViewAction.emit(this.action);
  }
}
