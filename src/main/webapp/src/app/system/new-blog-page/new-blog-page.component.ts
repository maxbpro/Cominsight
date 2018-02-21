import { Component, OnInit } from '@angular/core';
import {Blog} from "../../shared/models/blog.model";
import {User} from "../../shared/models/user.model";
import {Member} from "../../shared/models/member.model";
import {Company} from "../../shared/models/company.model";
import {AuthenticationService} from "../../shared/services/authentication.service";
import {BlogService} from "../../shared/services/blog.service";
import {ApiError} from "../../shared/models/error/api-error.model";

@Component({
  selector: 'app-new-blog-page',
  templateUrl: './new-blog-page.component.html'
})
export class NewBlogPageComponent implements OnInit {

  blog: Blog = new Blog();
  member: Member;

  currentDate: Date = new Date();

  error: ApiError;
  loading : boolean = false;
  success: boolean = false;

  constructor(private authService: AuthenticationService,
              private blogService: BlogService) { }

  ngOnInit() {
    this.member = this.authService.getMember();
  }

  createBlog(){

    if(this.member){

      this.blog.member = this.member;
      this.blog.company = this.member.company;

      this.blog.tags = this.blog.tagsString.split(',');

      this.success = false;
      this.loading = true;
      this.error = null;
      this.blogService.newBlog(this.blog).subscribe(data =>{
        this.loading = false;
        this.success = true;

      }, error => {
        this.error = error;
        this.loading = false;
      });
    }
  }

}
