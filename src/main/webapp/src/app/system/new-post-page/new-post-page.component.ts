import { Component, OnInit } from '@angular/core';
import {PostService} from "../../shared/services/post.service";
import {Post} from "../../shared/models/post.model";
import {Company} from "../../shared/models/company.model";
import {Member} from "../../shared/models/member.model";
import {User} from "../../shared/models/user.model";
import {Ng2FileInputAction} from "ng2-file-input";
import {AuthenticationService} from "../../shared/services/authentication.service";
import {ApiError} from "../../shared/models/error/api-error.model";

@Component({
  selector: 'app-new-post-page',
  templateUrl: './new-post-page.component.html'
})
export class NewPostPageComponent implements OnInit {

  post: Post = new Post();
  member: Member;

  currentDate: Date = new Date();
  categories: string[] = [];

  file: File;
  selectedImageUrl: string;

  error: ApiError;
  loading: boolean = false;
  success: boolean = false;

  constructor(private postService: PostService,
              private authService: AuthenticationService) { }

  ngOnInit() {

    this.member = this.authService.getMember();
    this.categories = ['Creative', 'Digital'];
  }

  createPost(){

    if(this.member && this.file){

      this.post.member = this.member;
      this.post.company = this.member.company;

      this.post.tags = this.post.tagsString.split(',');

      this.loading = true;
      this.success = false;
      this.error = null;
      this.postService.newPost(this.file, this.post).subscribe(data =>{

        this.success = true;
        this.loading = false;

      }, error => {
        this.error = error;
        this.loading = false;
      });
    }


  }

  onPhotoAction(event){

    // Removed=0,
    //   Added= 1,
    //   InvalidDenied = 2,
    //   CouldNotRemove = 3,
    //   CouldNotAdd = 4,

    if(event.action===Ng2FileInputAction.Added){
      this.file = event.file;
    }
  }



}
