import { Component, OnInit } from '@angular/core';
import {UserService} from "../../shared/services/user.service";
import {ApiError} from "../../shared/models/error/api-error.model";

@Component({
  selector: 'app-reset-password-page',
  templateUrl: './reset-password-page.component.html',
  styleUrls: ['./reset-password-page.component.css']
})
export class ResetPasswordPageComponent implements OnInit {

  emailString: string = "";

  error: ApiError;
  loading: boolean = false;
  success: boolean = false;

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  send(){

    this.loading = true;
    this.success = false;
    this.error = null;
    this.userService.resetPassword(this.emailString).subscribe(user =>{
      this.loading = false;
      this.success = true;

    }, error =>{
      this.error = error;
      this.loading = false;
    });
  }
}
