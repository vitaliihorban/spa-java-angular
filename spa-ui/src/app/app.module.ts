import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EmployeesComponent } from './components/employees/employees.component';
import { EmployeeComponent } from './components/employee/employee.component';
import {
  MatButtonModule,
  MatCardModule, MatDividerModule, MatFormFieldModule, MatGridListModule, MatIconModule, MatInputModule, MatListModule, MatOptionModule,
  MatPaginatorModule,
  MatProgressBarModule, MatProgressSpinnerModule,
  MatSelectModule,
  MatTableModule,
  MatToolbarModule
} from '@angular/material';
import {RouterModule, Routes} from '@angular/router';
import { MainComponent } from './components/main/main.component';
import { EditEmployeeComponent } from './components/edit-employee/edit-employee.component';
import {FormsModule} from '@angular/forms';
import { NotFoundComponent } from './components/not-found/not-found.component';
import {HttpClientModule} from '@angular/common/http';
import { LoginComponent } from './components/login/login.component';
import {AuthGuardService} from './services/auth.guard.service';
import {AuthService} from './services/auth.service';

const appRoutes: Routes = [
  { path: '', component: MainComponent, canActivate: [AuthGuardService] },
  { path: 'login', component: LoginComponent },

  { path: '**', component: NotFoundComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    EmployeesComponent,
    EmployeeComponent,
    MainComponent,
    EditEmployeeComponent,
    NotFoundComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatPaginatorModule,
    MatToolbarModule,
    MatCardModule,
    MatProgressBarModule,
    MatFormFieldModule,
    MatOptionModule,
    MatSelectModule,
    MatButtonModule,
    MatInputModule,
    MatDividerModule,
    MatListModule,
    MatGridListModule,
    MatIconModule,
    HttpClientModule,
      MatProgressSpinnerModule,
    RouterModule.forRoot(
        appRoutes,
        { enableTracing: true } // <-- debugging purposes only
    )
  ],
  providers: [AuthGuardService, AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
