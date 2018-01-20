import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ActivatedRoute} from "@angular/router";
import {AlertService} from "../services/alert.service";
import {AuthenticationService} from "../services/authentication.service";
import {CompanyService} from "../services/company.service";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  model: any = {};
  loading = false;
  returnUrl: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private companyService: CompanyService,
    private userService: UserService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService) { }

  ngOnInit() {
    // reset login status
    this.authenticationService.logout();

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';

    // this.companyService.getAll().subscribe(companies =>{
    //   var next2 = companies[0];
    // });
    //
    // this.userService.getAll().subscribe(users => {
    //   var user = users[0];
    // });
    //
    // this.userService.getById("5a63865bcda2e8f579a765b6").subscribe(user=>{
    //   this.alertService.error(user.firstName);
    // }, error => {
    //   this.alertService.error(error.statusText);
    // });

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
