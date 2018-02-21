import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../../shared/services/user.service";
import {Subscription} from "rxjs/Subscription";
import {ActivatedRoute} from "@angular/router";
import {ApiError} from "../../shared/models/error/api-error.model";

@Component({
  selector: 'app-update-password-page',
  templateUrl: './update-password-page.component.html',
  styleUrls: ['./update-password-page.component.css']
})
export class UpdatePasswordPageComponent implements OnInit, OnDestroy {

  loading: boolean = false;
  model : any ={};

  sub: Subscription;
  userId: string;
  token: string;

  error: ApiError;
  success: boolean = false;

  constructor(private route: ActivatedRoute,
              private userService: UserService) {
    this.sub = this.route.params.subscribe(params => {
      this.userId = params['userId'];
      this.token = params['token'];
    });
  }

  ngOnInit() {
  }

  updatePassword(){

    if(this.model.password === this.model.matchingPassword){

      this.loading = true;
      this.success = false;
      this.error = null;
      this.userService.updatePassword(this.userId, this.token, this.model.password).subscribe(user =>{

        this.loading = false;
        this.success = true;

      }, error =>{
        this.error = error;
        this.loading = false;
      });

    }else{
      this.error = new ApiError();
      this.error.debugMessage = "Passwords are not the same";
    }


  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
