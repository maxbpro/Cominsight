import { Component, OnInit } from '@angular/core';
import {User} from "../../shared/models/user.model";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../shared/services/authentication.service";
import {AlertService} from "../../shared/services/alert.service";
import {Company} from "../../shared/models/company.model";
import {ApiError} from "../../shared/models/error/api-error.model";

@Component({
  selector: 'app-new-user-page',
  templateUrl: './new-user-page.component.html',
  styleUrls: ['./new-user-page.component.css']
})
export class NewUserPageComponent {

  user: User = new User();
  company: Company;

  loading = false;
  error: ApiError;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService) {}


  companySelected(company: Company) {
    this.company = company;
    this.user.company = this.company;
  }

  register() {

    this.loading = true;
    this.authenticationService.register(this.user)
      .subscribe(
        data => {
          this.loading = false;
          this.router.navigate(['/system']);
        },
        error => {
          this.error = error;
          this.loading = false;
        });

  }

}
