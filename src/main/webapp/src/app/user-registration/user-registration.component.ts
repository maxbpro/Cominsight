import {Component, Input, OnInit} from '@angular/core';
import {UserService} from '../services/user.service';
import {AlertService} from "../services/alert.service";
import {Router} from "@angular/router";
import {User} from "../models/user";
import {Company} from "../models/company";

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html'
})
export class UserRegistrationComponent {

  model: User = new User();
  loading = false;

  constructor(
    private router: Router,
    private userService: UserService,
    private alertService: AlertService) { }



  register() {
    this.loading = true;
    this.model.company = new Company({id:"5a6385e4cda2e8f579a765b5"});
    this.userService.register(this.model)
      .subscribe(
        user => {
          this.alertService.success('Registration successful', true);
          this.router.navigate(['/']);
        },
        error => {
          this.alertService.error(error);
          this.loading = false;
        });
  }

}
