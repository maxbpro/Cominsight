import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs/Subscription";
import {Company} from "../../shared/models/company.model";
import {Member} from "../../shared/models/member.model";
import {CompanyService} from "../../shared/services/company.service";
import {MemberService} from "../../shared/services/member.service";
import {ApiError} from "../../shared/models/error/api-error.model";

@Component({
  selector: 'app-member-page',
  templateUrl: './member-page.component.html'
})
export class MemberPageComponent implements OnInit, OnDestroy {

  id: string;
  sub: Subscription;

  member: Member;

  error: ApiError;
  loaded: boolean = false;
  loading: boolean = false;

  constructor(private route: ActivatedRoute,
              private memberService : MemberService) {

    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
    });
  }

  ngOnInit() {
    this.getMemberInfo();
  }

  getMemberInfo(){

    this.memberService.getUserById(this.id).subscribe(member =>{

       this.member = member;
       this.loaded = true;
       this.loading = false;
      },
      error => {
        this.error = error;
        this.loading = false;
      });
  }



  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
