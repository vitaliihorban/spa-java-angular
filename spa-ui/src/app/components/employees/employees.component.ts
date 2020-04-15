import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {MatPaginator, MatTableDataSource, PageEvent} from '@angular/material';
import {EmployeeService} from '../../services/employee.service';
import {Employee} from '../../app.models';


@Component({
    selector: 'app-employees',
    templateUrl: './employees.component.html',
    styleUrls: ['./employees.component.css']
})
export class EmployeesComponent implements OnInit, OnChanges {

    id: number;
    action: string;
    employees: any = [];
    pageSize = 5;
    pageIndex = 0;
    length;
    pageEvent: PageEvent;
    name = '';
    paginator;

    @Output()
    startAction = new EventEmitter<{ id: number, action: string }>();
    displayedColumns: string[] = ['view-action', 'edit-action', 'id', 'name', 'isActive', 'department.name', 'delete-action'];
    dataSource = new MatTableDataSource<Employee>(this.employees);

    constructor(private employeeService: EmployeeService) {
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.getEmployees();
    }

    ngOnInit() {
        this.dataSource.paginator = this.paginator;
        this.getEmployees();
    }

    getPageOfEmployees(event?: PageEvent) {
        this.employeeService.getAllEmployees(event.pageIndex, event.pageSize, this.name)
            .subscribe(
                result => {
                    this.employees = result.content;
                    this.dataSource = new MatTableDataSource<Employee>(this.employees);
                    this.pageIndex = result.number;
                    this.pageSize = result.size;
                    this.length = result.totalElements;
                }
            );
    }

    editEmployee(id) {
        this.id = id;
        this.action = 'edit';
        this.startAction.emit({id: this.id, action: this.action});
    }

    viewEmployee(id) {
        this.id = id;
        this.action = 'view';
        this.startAction.emit({id: this.id, action: this.action});
    }

    deleteEmployee(id) {
        this.employeeService.deleteEmployee(id)
            .subscribe(
                result => {
                    this.getEmployees();
                }
            );
    }

    getEmployees() {
        this.employeeService.getAllEmployees(this.pageIndex, this.pageSize, this.name)
            .subscribe(
                result => {
                    this.employees = result.content;
                    this.dataSource = new MatTableDataSource<Employee>(this.employees);
                    this.pageIndex = result.number;
                    this.pageSize = result.size;
                    this.length = result.totalElements;
                }
            );
    }
}

