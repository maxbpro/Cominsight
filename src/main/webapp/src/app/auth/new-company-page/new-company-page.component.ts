import {Component, OnInit} from '@angular/core';
import {AlertService} from "../../shared/services/alert.service";
import {CompanyService} from "../../shared/services/company.service";
import {Router} from "@angular/router";
import {Company} from "../../shared/models/company.model";
import {ApiError} from "../../shared/models/error/api-error.model";

@Component({
  selector: 'app-new-company-page',
  templateUrl: './new-company-page.component.html',
  styleUrls: ['./new-company-page.component.css']
})
export class NewCompanyPageComponent implements OnInit{

  categories: string[] = [];
  company: Company = new Company();
  loading = false;

  error: ApiError;

  constructor(
    private router: Router,
    private companyService: CompanyService) { }


  ngOnInit(): void {

    this.categories = ['Creative', 'Digital', 'Public group'];
  }

  register() {
    this.loading = true;
    this.companyService.newCompany(this.company)
      .subscribe(company => {

          this.loading = false;
          this.router.navigate(['/new_user']);
        },
        error => {
          this.error = error;
          this.loading = false;
        });
  }

}
