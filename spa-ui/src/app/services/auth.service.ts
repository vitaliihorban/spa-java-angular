import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../app.models';
import {environment} from '../../environments/environment';

@Injectable()
export class AuthService {

  constructor(private http: HttpClient) { }

  public isAuthenticated(): boolean {
      const username = sessionStorage.getItem('username');
      const password = sessionStorage.getItem('password');
      return (!!username && !!password);
  }

  public login(user: User): Observable<any> {
    const headers =  new HttpHeaders({ Authorization: 'Basic ' + btoa(user.username + ':' + user.password) });
    return this.http.get(environment.login, {headers});
  }
}
