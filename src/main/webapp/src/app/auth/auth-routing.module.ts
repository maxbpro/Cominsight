import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthComponent } from './auth.component';
import {ContactsPageComponent} from "./contacts-page/contacts-page.component";
import {NewCompanyPageComponent} from "./new-company-page/new-company-page.component";
import {NewUserPageComponent} from "./new-user-page/new-user-page.component";
import {HomePageComponent} from "./home-page/home-page.component";
import {ResetPasswordPageComponent} from "./reset-password-page/reset-password-page.component";
import {ConfirmEmailPageComponent} from "./confirm-email-page/confirm-email-page.component";
import {UpdatePasswordPageComponent} from "./update-password-page/update-password-page.component";

const routes: Routes = [
  {path: '', component: AuthComponent, children: [
    {path: 'login', component: HomePageComponent},
    {path: 'new_user', component: NewUserPageComponent},
    {path: 'new_company', component: NewCompanyPageComponent},
    {path: 'contacts', component: ContactsPageComponent},
    {path: 'reset', component: ResetPasswordPageComponent},
    {path: 'update_password/:userId/:token', component: UpdatePasswordPageComponent},
    {path: 'confirm/:id', component: ConfirmEmailPageComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule {}
