import {Component, Input, OnInit} from '@angular/core';
import {Post} from "../../../shared/models/post.model";

@Component({
  selector: 'app-timeline-item',
  templateUrl: './timeline-item.component.html'
})
export class TimelineItemComponent implements OnInit {

  @Input() post: Post;


  constructor() { }

  ngOnInit() {

  }

}
