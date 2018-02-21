import { Component, OnInit } from '@angular/core';
import {User} from "../../shared/models/user.model";
import {UserService} from "../../shared/services/user.service";
import {AuthenticationService} from "../../shared/services/authentication.service";
import {Ng2FileInputAction} from "ng2-file-input";
import {ApiError} from "../../shared/models/error/api-error.model";

@Component({
  selector: 'app-my-profile-page',
  templateUrl: './my-profile-page.component.html'
})
export class MyProfilePageComponent implements OnInit {

  user: User;
  userId: string;

  error: ApiError;
  loading = false;
  success: boolean = false;

  constructor(private userService : UserService,
              private authService: AuthenticationService) { }

  ngOnInit() {

    this.userId = this.authService.getMember().id;

    this.getUserInfo();
  }

  getUserInfo(){

    this.loading = true;
    this.userService.getUserById(this.userId).subscribe(user =>{

      this.user = user;
      this.authService.setUser(user);
      this.loading = false;

    }, error =>{
      this.error = error;
      this.loading = false;
    });
  }

  update(){

    this.loading = true;
    this.success = false;
    this.userService.updateUser(this.userId, this.user).subscribe(user =>{

      this.user = user;
      this.loading = false;
      this.success = true;
      this.authService.setUser(user);

    }, error =>{
      this.error = error;
      this.loading = false;
    });
  }



  onPhotoAction(event){

    // Removed=0,
    //   Added= 1,
    //   InvalidDenied = 2,
    //   CouldNotRemove = 3,
    //   CouldNotAdd = 4,

    if(event.action===Ng2FileInputAction.Added){
      this.updateAvatar(event.file);
    }
  }

  updateAvatar(file){

    this.loading = true;
    this.success = false;
    this.userService.updateAvatar(file).subscribe(user =>{

      this.user = user;
      this.loading = false;
      this.success = true;
      this.authService.setUser(user);

    }, error =>{
      this.error = error;
      this.loading = false;
    });
  }
}
