import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Company} from "../../../shared/models/company.model";
import {Subscription} from "rxjs/Subscription";
import {UserService} from "../../../shared/services/user.service";
import {Member} from "../../../shared/models/member.model";
import {ApiError} from "../../../shared/models/error/api-error.model";
import {MemberService} from "../../../shared/services/member.service";

@Component({
  selector: 'app-member-details',
  templateUrl: './member-details.component.html'
})
export class MemberDetailsComponent implements OnInit, OnDestroy {

  userId: string;
  sub: Subscription;
  member: Member;
  error: ApiError;

  loaded: boolean = false;

  constructor(private memberService: MemberService,
              private route: ActivatedRoute) {
    this.sub = this.route.params.subscribe(params => {
      this.userId = params['id'];
    });
  }

  ngOnInit() {

    this.memberService.getUserById(this.userId).subscribe(member =>{

        this.member = member;
        this.loaded = true;
      },
      error => {
        this.error = error;
        this.loaded = false;
      });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
