import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs/Subscription";
import {Company} from "../../shared/models/company.model";
import {CompanyService} from "../../shared/services/company.service";
import {ApiError} from "../../shared/models/error/api-error.model";

@Component({
  selector: 'app-company-page',
  templateUrl: './company-page.component.html',
  styleUrls: ['./company-page.component.css']
})
export class CompanyPageComponent implements OnInit, OnDestroy {

  id: string;
  sub: Subscription;

  company: Company;

  loaded: boolean = false;
  loading = false;
  error: ApiError;

  constructor(private route: ActivatedRoute,
              private companyService: CompanyService) {

    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
    });
  }

  ngOnInit() {
    this.getCompanyInfo();
  }

  getCompanyInfo(){

    this.loading = true;
    this.companyService.getById(this.id).subscribe(company =>{

        this.company = company;
        this.loaded = true;
        this.loading = false;

      },error => {
        this.error = error;
        this.loading = false;
      });
  }

  startFollowing(){
    
    this.companyService.startFollowing(this.id).subscribe(company => {

      this.company = company;
      this.loading = false;

    }, error => {
      this.error = error;
      this.loading = false;
    });
  }

  stopFollowing(){

    this.companyService.stopFollowing(this.id).subscribe(company => {

      this.company = company;
      this.loading = false;

    }, error => {
      this.error = error;
      this.loading = false;
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
