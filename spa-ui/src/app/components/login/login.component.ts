import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {User} from '../../app.models';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User = {
    username: '',
    password: ''
  };

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
  }

  login() {
    this.authService.login(this.user)
        .subscribe( res => {
          sessionStorage.setItem('username', this.user.username);
          sessionStorage.setItem('password', this.user.password);
          this.router.navigateByUrl('/');
        },
            err => {
              console.log('Error');
            });
  }
}
