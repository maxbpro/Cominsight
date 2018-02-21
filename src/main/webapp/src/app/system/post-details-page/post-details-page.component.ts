import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Post} from "../../shared/models/post.model";
import {Subscription} from "rxjs/Subscription";
import {ActivatedRoute} from "@angular/router";
import {PostService} from "../../shared/services/post.service";
import {Comment} from "../../shared/models/comment.model";
import {CommentService} from "../../shared/services/comment.service";
import {AuthenticationService} from "../../shared/services/authentication.service";
import {Member} from "../../shared/models/member.model";
import {ApiError} from '../../shared/models/error/api-error.model';

@Component({
  selector: 'app-post-details-page',
  templateUrl: './post-details-page.component.html'
})
export class PostDetailsPageComponent implements OnInit, OnDestroy {


  id: string;
  sub: Subscription;

  post: Post;
  member: Member;

  comments: Comment[] = [];

  creatingComment: boolean = false;

  currentPage: number = 0;
  currentComment: Comment = new Comment();

  error: ApiError;
  loading = false;
  loaded: boolean = false;
  commentsLoaded: boolean = false;

  constructor(private route: ActivatedRoute,
              private postService: PostService,
              private authService: AuthenticationService,
              private commentService: CommentService) {

    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
    });
  }

  ngOnInit() {

      this.member = this.authService.getMember();

      this.loading = true;
      this.postService.getPostById(this.id).subscribe(post =>{

        this.post = post;
        this.loaded = true;
        this.loading = false;

      }, error =>{

        this.error = error;
        this.loading = false;
      });

      this.commentService.getAllCommentsOfPost(this.id, this.currentPage, 10).subscribe(page => {

        this.currentPage = page.currentPage;
        let items = page.content;

        items.forEach(el =>{
          this.comments.push(el);
        });

        this.commentsLoaded = true;


      }, error =>{
        this.error = error;
        this.loading = false;
      });

  }

  createComment(){

    if(this.member){

      this.currentComment.member = this.member;
      this.currentComment.post = this.post;

      this.creatingComment = true;
      this.commentService.newComment(this.currentComment).subscribe(comment => {

        this.comments.push(comment);
        this.creatingComment = false;
        this.currentComment = new Comment();

      }, error =>{
        this.creatingComment = false;
        this.error = error;
        this.loading = false;
      });

    }


  }



  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
