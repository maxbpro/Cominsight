import {Routes, RouterModule, PreloadAllModules} from '@angular/router';
import { NgModule } from '@angular/core';
import {NotFoundComponent} from "./shared/components/not-found/not-found.component";

const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'system', loadChildren: './system/system.module#SystemModule'},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    preloadingStrategy: PreloadAllModules
  })],
  exports: [RouterModule]
})
export class AppRoutingModule {}


// const appRoutes: Routes = [
//   { path: '', component: HomeComponent, canActivate: [AuthGuard] },
//
//   { path: 'company_register', component: CompanyRegistrationComponent },
//   { path: 'company_edit', component: CompanyEditComponent },
//   { path: 'company', component: CompanyComponent },
//
//   { path: 'register', component: UserRegistrationComponent },
//   { path: 'login', component: LoginComponent },
//   { path: 'user_edit', component: UserEditComponent },
//
//
//   // otherwise redirect to home
//   { path: '**', redirectTo: '' }
// ];

