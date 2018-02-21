import { Component, OnInit } from '@angular/core';
import {ApiError} from "../../shared/models/error/api-error.model";
import {UserService} from "../../shared/services/user.service";
import {AuthenticationService} from "../../shared/services/authentication.service";
import {Member} from "../../shared/models/member.model";

@Component({
  selector: 'app-change-password-page',
  templateUrl: './change-password-page.component.html',
  styleUrls: ['./change-password-page.component.css']
})
export class ChangePasswordPageComponent implements OnInit {

  model: any = {};
  member: Member;

  error: ApiError;
  loading = false;
  success: boolean = false;

  constructor(private userService : UserService,
              private authService: AuthenticationService) { }

  ngOnInit() {
    this.member = this.authService.getMember();
  }

  update(){

    if(this.model.password === this.model.matchingPassword){

      this.loading = true;
      this.success = false;
      this.error = null;
      this.userService.changePassword(this.model.old, this.model.password).subscribe(user =>{

        this.loading = false;
        this.success = true;
        this.authService.setUser(user);

      }, error =>{
        this.error = error;
        this.loading = false;
      });

    }else{
      this.error = new ApiError();
      this.error.debugMessage = "Passwords are not the same";
    }


  }
}
