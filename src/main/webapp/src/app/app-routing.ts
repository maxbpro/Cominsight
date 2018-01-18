import { Routes, RouterModule } from '@angular/router';


import { HomeComponent } from './home/index';
import { LoginComponent } from './login/index';
//import { RegisterComponent } from './register/index';
import { AuthGuard } from './guards/index';
import {UserRegistrationComponent} from "./user-registration/user-registration.component";
import {CompanyRegistrationComponent} from "./company-registration/company-registration.component";

const appRoutes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: UserRegistrationComponent },
  { path: 'company_register', component: CompanyRegistrationComponent },

  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);
