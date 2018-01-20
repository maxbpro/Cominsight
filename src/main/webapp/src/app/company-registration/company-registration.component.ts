import { Component } from '@angular/core';
import {CompanyService} from "../services/company.service";
import {Router} from '@angular/router';
import {AlertService} from "../services/alert.service";

@Component({
  selector: 'app-company-registration',
  templateUrl: './company-registration.component.html'
})
export class CompanyRegistrationComponent  {

  model: any = {};
  loading = false;

  constructor(
    private router: Router,
    private companyService: CompanyService,
    private alertService: AlertService) { }

  register() {
    this.loading = true;
    this.companyService.create(this.model)
      .subscribe(
        data => {
          this.alertService.success('Registration successful', true);
          this.router.navigate(['/login']);
        },
        error => {
          this.alertService.error(error);
          this.loading = false;
        });
  }
}
