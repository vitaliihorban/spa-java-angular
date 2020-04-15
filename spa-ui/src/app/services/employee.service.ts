import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Employee} from '../app.models';
import {Observable} from 'rxjs';


@Injectable({
    providedIn: 'root'
})
export class EmployeeService {

    constructor(private http: HttpClient) { }

    createHeaders() {
        const username = sessionStorage.getItem('username');
        const password = sessionStorage.getItem('password');
        const headers = new  HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
        return headers;

    }

    getAllEmployees(page, size, name): Observable<any> {
        const headers = this.createHeaders();
        return this.http.get(environment.employees + '?name=' + name + '&page=' + page + '&size=' + size , {headers});
    }

    getEmployeeById(id): Observable<any> {
        const headers = this.createHeaders();
        return this.http.get(environment.employees + '/' + id, {headers});
    }

    deleteEmployee(id): Observable<any> {
        const headers = this.createHeaders();
        return this.http.delete(environment.employees + '/' + id, {headers});
    }

    updateEmployee(employee: Employee): Observable<any> {
        const headers = this.createHeaders();
        return this.http.put(environment.employees + '/' + employee.id, employee, {headers});
    }

    getDepartments(): Observable<any> {
        const headers = this.createHeaders();
        return this.http.get(environment.departments, {headers});
    }
}

