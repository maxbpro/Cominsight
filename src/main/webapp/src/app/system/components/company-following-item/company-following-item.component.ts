import {Component, Input, OnInit} from '@angular/core';
import {Following} from "../../../shared/models/following.model";

@Component({
  selector: 'app-company-following-item',
  templateUrl: './company-following-item.component.html'
})
export class CompanyFollowingItemComponent implements OnInit {

  @Input() following: Following = new Following();
  loaded: boolean = false;

  constructor() { }

  ngOnInit() {
    this.loaded = true;
  }



}
