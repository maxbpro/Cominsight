import { Component, OnInit } from '@angular/core';
import {Blog} from "../../shared/models/blog.model";
import {Comment} from "../../shared/models/comment.model";
import {ApiError} from "../../shared/models/error/api-error.model";

@Component({
  selector: 'app-blog-page',
  templateUrl: './blog-page.component.html'
})
export class BlogPageComponent implements OnInit {

  blog: Blog;
  comments: Comment[] = [];
  loading = false;

  error: ApiError;

  constructor() { }

  ngOnInit() {

    

  }

}
