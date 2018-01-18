import { Component, OnInit } from '@angular/core';
import {CompanyService} from "../services/company.service";
import {Company} from "../models/company";
import {Router} from '@angular/router';
import {AlertService} from "../services/alert.service";

@Component({
  selector: 'app-company-registration',
  templateUrl: './company-registration.component.html'
})
export class CompanyRegistrationComponent implements OnInit {

  model: any = {};
  loading = false;

  constructor(
    private companyService: CompanyService,
    private router: Router,
    private alertService: AlertService) {}

  ngOnInit() {
  }

  register(event){
    event.preventDefault();

    // this.companyService.addCompany(new Company("", ""));
    //
    // this.companyService.getAllCompanies().subscribe(data => {
    //   var results = data['results'];
    // })




    this.loading = true;
    this.companyService.addCompany(this.model)
      .subscribe(
        data => {
          // set success message and pass true paramater to persist the message after redirecting to the login page
          this.alertService.success('Registration successful', true);
          this.router.navigate(['/login']);
        },
        error => {
          this.alertService.error(error);
          this.loading = false;
        });
  }
}
