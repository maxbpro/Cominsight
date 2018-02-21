import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-notice-item',
  templateUrl: './notice-item.component.html'
})
export class NoticeItemComponent implements OnInit {

  @Input() text: String;

  constructor(

  ) { }

  ngOnInit() {
    this.text = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some look even slightly believable";
  }

}
