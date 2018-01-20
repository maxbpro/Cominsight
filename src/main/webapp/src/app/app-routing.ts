import { Routes, RouterModule } from '@angular/router';


import { HomeComponent } from './home/index';
import { LoginComponent } from './login/index';
import { AuthGuard } from './guards/index';
import {UserRegistrationComponent} from "./user-registration/user-registration.component";
import {CompanyRegistrationComponent} from "./company-registration/company-registration.component";
import {UserEditComponent} from "./user-edit/user-edit.component";
import {CompanyEditComponent} from "./company-edit/company-edit.component";
import {CompanyComponent} from "./company/company.component";

const appRoutes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },

  { path: 'company_register', component: CompanyRegistrationComponent },
  { path: 'company_edit', component: CompanyEditComponent },
  { path: 'company', component: CompanyComponent },

  { path: 'register', component: UserRegistrationComponent },
  { path: 'login', component: LoginComponent },
  { path: 'user_edit', component: UserEditComponent },


  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);
