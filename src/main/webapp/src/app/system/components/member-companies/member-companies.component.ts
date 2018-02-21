import {Component, OnDestroy, OnInit} from '@angular/core';
import {Company} from "../../../shared/models/company.model";
import {Subscription} from "rxjs/Subscription";
import {CompaniesPage} from "../../../shared/models/pages/companies.page.model";
import {CompanyService} from "../../../shared/services/company.service";
import {ActivatedRoute} from "@angular/router";
import {Following} from "../../../shared/models/following.model";
import {MemberService} from "../../../shared/services/member.service";

@Component({
  selector: 'app-member-companies',
  templateUrl: './member-companies.component.html'
})
export class MemberCompaniesComponent implements OnInit, OnDestroy {

  userId: string;
  sub: Subscription;
  loaded: boolean = false;

  companies: Following[] = [];
  page: CompaniesPage;

  currentPage: number = 0;

  constructor(private memberService: MemberService,
              private route: ActivatedRoute) {

    this.sub = this.route.params.subscribe(params => {
      this.userId = params['id'];
    });
  }

  ngOnInit() {

    this.getMemberInfo();
  }

  getMemberInfo(){

    this.memberService.getUserById(this.userId).subscribe(member =>{

        this.companies = member.following;
        this.loaded = true;
      },
      error => {
        // error - объект ошибки
      });
  }


  movePrev(){

  }

  moveNext(){

  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
