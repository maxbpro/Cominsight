import { Component, OnInit } from '@angular/core';
import {Company} from "../../shared/models/company.model";
import {Member} from "../../shared/models/member.model";
import {CompanyService} from "../../shared/services/company.service";
import {AuthenticationService} from "../../shared/services/authentication.service";
import {UserService} from "../../shared/services/user.service";
import {Ng2FileInputAction} from "ng2-file-input";
import {ApiError} from "../../shared/models/error/api-error.model";

@Component({
  selector: 'app-my-company-page',
  templateUrl: './my-company-page.component.html'
})
export class MyCompanyPageComponent implements OnInit {

  company: Company;
  member: Member;
  categories: string[] = [];

  error: ApiError;
  loading = false;
  success: boolean = false;

  constructor(private companyService: CompanyService,
              private userService: UserService,
              private authService: AuthenticationService) { }

  ngOnInit() {
    this.member = this.authService.getMember();
    this.categories = ['Creative', 'Digital', 'Public group'];

    this.getCompany();
  }


  getCompany(){

    this.loading = true;
    this.companyService.getById(this.member.company.id).subscribe(company =>{

      this.company = company;
      this.loading = false;

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

  update(){

    this.loading = true;
    this.success = false;
    this.companyService.updateCompany(this.company.id, this.company).subscribe(company =>{

      this.company = company;
      this.loading = false;
      this.success = true;

      this.updateUserInfoInStorage();

    }, error => {
        this.error = error;
        this.loading = false;
    });
  }

  updateAvatar(file){

    this.loading = true;
    this.success = false;
    this.companyService.updatePhoto(file).subscribe(company =>{

      this.company = company;
      this.loading = false;
      this.success = true;

      this.updateUserInfoInStorage();

    }, error =>{
      this.error = error;
      this.loading = false;
    });
  }

  updateUserInfoInStorage(){

    this.userService.getUserById(this.member.id).subscribe(user =>{

      this.authService.setUser(user);
      this.loading = false;

    }, error =>{
      this.error = error;
      this.loading = false;
    });
  }

}
