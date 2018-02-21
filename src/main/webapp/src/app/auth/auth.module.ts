import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthComponent } from './auth.component';
import { AuthRoutingModule } from './auth-routing.module';
import { SharedModule } from '../shared/shared.module';
import { HeaderComponent } from './components/header/header.component';
import { SigninComponent } from './components/signin/signin.component';
import { HowItWorksComponent } from './components/how-it-works/how-it-works.component';
import { FeaturesComponent } from './components/features/features.component';
import { MostPopularComponent } from './components/most-popular/most-popular.component';
import { FrequentlyAskedQuestionComponent } from './components/frequently-asked-question/frequently-asked-question.component';
import { ContactsPageComponent } from './contacts-page/contacts-page.component';
import { HomePageComponent } from './home-page/home-page.component';
import { NewCompanyPageComponent } from './new-company-page/new-company-page.component';
import { NewUserPageComponent } from './new-user-page/new-user-page.component';
import { SearchCompanyItemComponent } from './components/search-company-item/search-company-item.component';
import { SearchCompanyAuthcompleteComponent } from './components/search-company-authcomplete/search-company-authcomplete.component';
import { ResetPasswordPageComponent } from './reset-password-page/reset-password-page.component';
import { ConfirmEmailPageComponent } from './confirm-email-page/confirm-email-page.component';
import { UpdatePasswordPageComponent } from './update-password-page/update-password-page.component';

@NgModule({
  declarations: [
    AuthComponent,
    HeaderComponent,
    SigninComponent,
    HowItWorksComponent,
    FeaturesComponent,
    MostPopularComponent,
    FrequentlyAskedQuestionComponent,
    ContactsPageComponent,
    HomePageComponent,
    NewCompanyPageComponent,
    NewUserPageComponent,
    SearchCompanyItemComponent,
    SearchCompanyAuthcompleteComponent,
    ResetPasswordPageComponent,
    ConfirmEmailPageComponent,
    UpdatePasswordPageComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    SharedModule
  ]
})
export class AuthModule {}
