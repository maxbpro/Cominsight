import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/Subscription";
import {Post} from "../../../shared/models/post.model";
import {PostService} from "../../../shared/services/post.service";
import {ActivatedRoute} from "@angular/router";
import {PostsPage} from "../../../shared/models/pages/posts.page.model";

@Component({
  selector: 'app-company-media',
  templateUrl: './company-media.component.html'
})
export class CompanyMediaComponent implements OnInit, OnDestroy {


  companyId: string;
  sub: Subscription;
  loaded: boolean = false;

  posts: Post[] = [];
  page: PostsPage;

  currentPage: number = 0;

  constructor(private postService: PostService,
              private route: ActivatedRoute) {

    this.sub = this.route.params.subscribe(params => {
      this.companyId = params['id'];
    });
  }

  ngOnInit() {

    this.getInfo();

  }

  getInfo(){

    this.postService.getAllPostsOfCompany(this.companyId, this.currentPage, 10).subscribe(page =>{

        this.currentPage = page.currentPage;
        this.page = page;
        let items = page.content;

        items.forEach(element =>{
          this.posts.push(element);
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
