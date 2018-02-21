import {Component, Input, OnInit} from '@angular/core';
import {Replay} from "../../../shared/models/replay.model";

@Component({
  selector: 'app-comment-replay-item',
  templateUrl: './comment-replay-item.component.html'
})
export class CommentReplayItemComponent implements OnInit {

  @Input() replay:Replay;


  constructor() { }

  ngOnInit() {
  }



}
