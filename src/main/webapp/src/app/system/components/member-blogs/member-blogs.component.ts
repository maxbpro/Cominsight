import {Component, OnDestroy, OnInit} from '@angular/core';
import {Blog} from "../../../shared/models/blog.model";
import {Subscription} from "rxjs/Subscription";
import {BlogsPage} from "../../../shared/models/pages/blogs.page.model";
import {BlogService} from "../../../shared/services/blog.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-member-blogs',
  templateUrl: './member-blogs.component.html'
})
export class MemberBlogsComponent implements OnInit, OnDestroy {


  userId: string;
  sub: Subscription;
  loaded: boolean = false;

  blogs: Blog[] = [];
  page: BlogsPage;

  currentPage: number = 0;

  constructor(private blogService: BlogService,
              private route: ActivatedRoute) {

    this.sub = this.route.params.subscribe(params => {
      this.userId = params['id'];
    });
  }

  ngOnInit() {

    this.getInfo();

  }

  getInfo(){

    this.blogService.getAllBlogsOfMember(this.userId, this.currentPage, 10).subscribe(page =>{

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
