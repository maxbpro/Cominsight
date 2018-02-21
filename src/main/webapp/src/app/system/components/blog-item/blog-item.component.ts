import {Component, Input, OnInit} from '@angular/core';
import {Blog} from "../../../shared/models/blog.model";

@Component({
  selector: 'app-blog-item',
  templateUrl: './blog-item.component.html'
})
export class BlogItemComponent implements OnInit {

  @Input() blog:Blog;

  constructor() { }

  ngOnInit() {
  }

}
