import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/Subscription";
import {ActivatedRoute} from "@angular/router";
import {PostService} from "../../../shared/services/post.service";
import {Post} from "../../../shared/models/post.model";
import {PostsPage} from "../../../shared/models/pages/posts.page.model";

@Component({
  selector: 'app-member-media',
  templateUrl: './member-media.component.html'
})
export class MemberMediaComponent implements OnInit, OnDestroy {

  userId: string;
  sub: Subscription;
  loaded: boolean = false;

  posts: Post[] = [];
  page: PostsPage;

  currentPage: number = 0;

  constructor(private postService: PostService,
              private route: ActivatedRoute) {

    this.sub = this.route.params.subscribe(params => {
      this.userId = params['id'];
    });
  }

  ngOnInit() {

    this.getInfo();

  }

  getInfo(){

    this.postService.getAllPostsOfMember(this.userId, this.currentPage, 10).subscribe(page =>{

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
