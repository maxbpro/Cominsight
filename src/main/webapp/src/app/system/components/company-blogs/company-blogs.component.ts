import {Component, OnDestroy, OnInit} from '@angular/core';
import {Blog} from "../../../shared/models/blog.model";
import {Company} from "../../../shared/models/company.model";
import {Member} from "../../../shared/models/member.model";
import {Subscription} from "rxjs/Subscription";
import {BlogsPage} from "../../../shared/models/pages/blogs.page.model";
import {ActivatedRoute} from "@angular/router";
import {BlogService} from "../../../shared/services/blog.service";

@Component({
  selector: 'app-company-blogs',
  templateUrl: './company-blogs.component.html'
})
export class CompanyBlogsComponent implements OnInit, OnDestroy {

  companyId: string;
  sub: Subscription;
  loaded: boolean = false;

  blogs: Blog[] = [];
  page: BlogsPage;

  currentPage: number = 0;

  constructor(private blogService: BlogService,
              private route: ActivatedRoute) {

    this.sub = this.route.params.subscribe(params => {
      this.companyId = params['id'];
    });
  }

  ngOnInit() {

    this.getInfo();

  }

  getInfo(){

    this.blogService.getAllBlogsOfCompany(this.companyId, this.currentPage, 10).subscribe(page =>{

        this.currentPage = page.currentPage;
        this.page = page;
        let items = page.content;

        items.forEach(element =>{
          this.blogs.push(element);
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
