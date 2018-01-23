import {Component, Input, OnInit} from '@angular/core';
import {Post} from "../models/post";

@Component({
  selector: 'timeline-item',
  templateUrl: './timeline-item.component.html'
})
export class TimelineItemComponent implements OnInit {

  @Input()
  item: Post;

  constructor() { }

  ngOnInit() {
  }

}
