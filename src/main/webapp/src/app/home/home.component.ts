import { Component, OnInit } from '@angular/core';
import {CompanyService} from "../services/company.service";
import {TimelineService} from "../services/timeline.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

  constructor(private companyService: CompanyService,
              private timelineService: TimelineService) { }

  ngOnInit() {

    this.companyService.getAll().subscribe(data =>{
      var next2 = data[0];
    });

    this.timelineService.getTimeline().subscribe(data =>{
      var next2 = data[0];
    });
  }

}
