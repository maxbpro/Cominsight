import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Company} from "../../../shared/models/company.model";
import {Subscription} from "rxjs/Subscription";
import {CompanyService} from "../../../shared/services/company.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-company-details',
  templateUrl: './company-details.component.html'
})
export class CompanyDetailsComponent implements OnInit, OnDestroy {

  companyId: string;
  sub: Subscription;
  company: Company;

  loaded: boolean = false;

  constructor(private companyService: CompanyService,
              private route: ActivatedRoute) {
    this.sub = this.route.params.subscribe(params => {
      this.companyId = params['id'];
    });
  }

  ngOnInit() {

    this.loaded = false;
    this.companyService.getById(this.companyId).subscribe(company =>{

        this.company = company
        this.loaded = true;
      },
      error => {
        this.loaded = false;
      });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
