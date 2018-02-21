import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../../../shared/services/user.service";
import {Member} from "../../../shared/models/member.model";
import {MembersPage} from "../../../shared/models/pages/members.page.model";
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs/Subscription";

@Component({
  selector: 'app-company-members',
  templateUrl: './company-members.component.html'
})
export class CompanyMembersComponent implements OnInit, OnDestroy {

  companyId: string;
  sub: Subscription;
  loaded: boolean = false;

  members: Member[] = [];
  page: MembersPage;

  currentPage: number = 0;

  constructor(private userService: UserService,
              private route: ActivatedRoute) {

    this.sub = this.route.params.subscribe(params => {
      this.companyId = params['id'];
    });
  }

  ngOnInit() {

      this.getInfo();

  }

  getInfo(){

    this.userService.getAllMembersOfCompany(this.companyId, this.currentPage, 10).subscribe(page =>{

        this.currentPage = page.currentPage;
        this.page = page;
        let items = page.content;

        items.forEach(element =>{
          this.members.push(element);
        });

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
