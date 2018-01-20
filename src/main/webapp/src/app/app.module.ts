import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import { CompanyRegistrationComponent } from './company-registration/company-registration.component';
import {FormsModule} from "@angular/forms";
import { HomeComponent } from './home/home.component';
import {routing} from "./app-routing";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthGuard} from "./guards/auth.guard";
import {AlertService} from './services/alert.service';
import {AuthenticationService} from "./services/authentication.service";
import {CompanyService} from "./services/company.service";
import {UserService} from './services/user.service';
import {JwtInterceptor} from './helpers/jwt.interceptor';
import { AlertComponent } from './directives/alert/alert.component';
import { CompanyComponent } from './company/company.component';
import { CompanyEditComponent } from './company-edit/company-edit.component';
import { UserEditComponent } from './user-edit/user-edit.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    UserRegistrationComponent,
    CompanyRegistrationComponent,
    HomeComponent,
    AlertComponent,
    CompanyComponent,
    CompanyEditComponent,
    UserEditComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    routing,
    FormsModule
  ],
  providers: [
    AuthGuard,
    AlertService,
    AuthenticationService,
    CompanyService,
    UserService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
