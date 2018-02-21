import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ActivatedRoute} from "@angular/router";
import {AlertService} from "../../shared/services/alert.service";
import {AuthenticationService} from "../../shared/services/authentication.service";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html'
})
export class HomePageComponent implements OnInit {

  model: any = {};
  loading = false;
  returnUrl: string;

  emailFormControl: FormControl;
  passwordFormControl: FormControl;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService) { }

  ngOnInit() {

    this.authenticationService.logout();

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';

    this.emailFormControl = new FormControl('', [
      Validators.required
    ]);

    this.passwordFormControl = new FormControl('', [
      Validators.required
    ]);
  }

  login() {
    this.loading = true;
    this.authenticationService.login(this.model.username, this.model.password)
      .subscribe(
        data => {
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.alertService.error(error.statusText);
          this.loading = false;
        });
  }

}
