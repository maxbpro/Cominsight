import {Component, ElementRef, Output, EventEmitter, Input} from '@angular/core';
import {CompanyService} from "../../../shared/services/company.service";
import {Company} from "../../../shared/models/company.model";



@Component({
  selector: 'app-search-company-authcomplete',
  host: {
    '(document:click)': 'handleClick($event)',
  },
  templateUrl: './search-company-authcomplete.component.html',
  styleUrls: ['./search-company-authcomplete.component.css']
})
export class SearchCompanyAuthcompleteComponent {

  query = '';
  public filteredList: Company[] = [];
  public elementRef;

  loaded: boolean = false;

  @Output() onCompanySelected = new EventEmitter<Company>();

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

  select(company){
    this.query = company.title;
    this.filteredList = [];
    this.onCompanySelected.emit(company);
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
