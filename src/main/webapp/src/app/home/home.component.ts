import { Component, OnInit } from '@angular/core';
import {CompanyService} from "../services/company.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

  constructor(private companyService: CompanyService) { }

  ngOnInit() {

    this.companyService.getAll().subscribe(companies =>{
      var next2 = companies[0];
    });
  }

}
