import {Component, ElementRef, OnInit} from '@angular/core';
import {CompanyService} from "../../../shared/services/company.service";
import {Company} from "../../../shared/models/company.model";

@Component({
  selector: 'app-search-company-item',
  host: {
    '(document:click)': 'handleClick($event)',
  },
  templateUrl: './search-company-item.component.html',
  styleUrls: ['./search-company-item.component.css']
})
export class SearchCompanyItemComponent {

  public query = '';
  public filteredList: Company[] = [];
  public elementRef;

  loaded: boolean = false;

  constructor(myElement: ElementRef,
              private companyService: CompanyService) {
    this.elementRef = myElement;
  }


  filter() {
    if (this.query !== ""){

      this.companyService.search(this.query).subscribe(companies =>{

        this.filteredList = companies;

        this.loaded = true;
      });

    }else{
      this.filteredList = [];
    }
  }

  select(item){
    this.query = item;
    this.filteredList = [];
  }

  handleClick(event){
    let clickedComponent = event.target;
    let inside = false;
    do {
      if (clickedComponent === this.elementRef.nativeElement) {
        inside = true;
      }
      clickedComponent = clickedComponent.parentNode;
    } while (clickedComponent);
    if(!inside){
      this.filteredList = [];
    }
  }
}
