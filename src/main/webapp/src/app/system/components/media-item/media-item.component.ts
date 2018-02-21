import {Component, Input, OnInit} from '@angular/core';
import {Post} from "../../../shared/models/post.model";

@Component({
  selector: 'app-media-item',
  templateUrl: './media-item.component.html'
})
export class MediaItemComponent implements OnInit {

  @Input() post: Post;
  
  constructor() { }

  ngOnInit() {
  }

}
