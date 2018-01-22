import {Component} from '@angular/core';
import {AlertService} from "../services/alert.service";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../models/user";
import {Company} from "../models/company";
import {AuthenticationService} from "../services/authentication.service";

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html'
})
export class UserRegistrationComponent {

  model: User = new User();
  loading = false;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService) {

  }



  register() {
    this.loading = true;
    this.model.company = new Company({id:"5a6441cdcda2e803aaa5365a"});
    this.authenticationService.register(this.model)
      .subscribe(
        data => {
          this.router.navigate(['/']);
        },
        error => {
          this.alertService.error(error.statusText);
          this.loading = false;
        });

  }

}
