import {Component, Input, OnInit} from '@angular/core';
import {Company} from "../../../shared/models/company.model";

@Component({
  selector: 'app-company-item',
  templateUrl: './company-item.component.html'
})
export class CompanyItemComponent implements OnInit {

  @Input() company: Company = new Company();
  loaded: boolean = false;

  constructor() {
  }

  ngOnInit() {
    this.loaded = true;
  }

}
