import {Component, Input, OnInit} from '@angular/core';
import {Member} from "../../../shared/models/member.model";

@Component({
  selector: 'app-user-item',
  templateUrl: './user-item.component.html'
})
export class UserItemComponent implements OnInit {

  @Input() member: Member = new Member;

  constructor() { }

  ngOnInit() {
  }

}
