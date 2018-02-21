import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app-routing.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthGuard} from "./shared/guards/auth.guard";
import {AlertService} from './shared/services/alert.service';
import {AuthenticationService} from "./shared/services/authentication.service";
import {CompanyService} from "./shared/services/company.service";
import {UserService} from './shared/services/user.service';
import {JwtInterceptor} from './shared/helpers/jwt.interceptor';
import {VirtualScrollModule} from "angular2-virtual-scroll";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {AuthModule} from "./auth/auth.module";
import {NotFoundComponent} from "./shared/components/not-found/not-found.component";
import {MemberService} from "./shared/services/member.service";
import {Ng2FileInputModule} from "ng2-file-input";


@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent
  ],
  imports: [
    AuthModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    VirtualScrollModule
  ],
  providers: [
    AuthGuard,
    AlertService,
    AuthenticationService,
    MemberService,
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
