import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from "../../shared/services/user.service";
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs/Subscription";
import {ApiError} from "../../shared/models/error/api-error.model";

@Component({
  selector: 'app-confirm-email-page',
  templateUrl: './confirm-email-page.component.html',
  styleUrls: ['./confirm-email-page.component.css']
})
export class ConfirmEmailPageComponent implements OnInit, OnDestroy {

  loading: boolean = false;
  sub: Subscription;
  id: string;

  error: ApiError;
  success: boolean = false;

  constructor(private route: ActivatedRoute,
              private userService: UserService) {

    this.sub = this.route.params.subscribe(params => {
      this.id = params['id'];
    });
  }

  ngOnInit() {
  }

  confirm(){

    this.loading = true;
    this.success = false;
    this.error = null;
    this.userService.confirmEmail(this.id).subscribe(data =>{

      this.loading = false;
      this.success = true;

    }, error =>{

      this.error = error;
      this.loading = false;
      this.success = false;

    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }


}
