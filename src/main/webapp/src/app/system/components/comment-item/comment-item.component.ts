import {Component, Input, OnInit} from '@angular/core';
import {Replay} from "../../../shared/models/replay.model";
import {Member} from "../../../shared/models/member.model";
import {Comment} from "../../../shared/models/comment.model";
import {CommentService} from "../../../shared/services/comment.service";
import {AuthenticationService} from "../../../shared/services/authentication.service";
import {ApiError} from "../../../shared/models/error/api-error.model";

@Component({
  selector: 'app-comment-item',
  templateUrl: './comment-item.component.html'
})
export class CommentItemComponent implements OnInit {

  @Input() comment:Comment;

  replays: Replay[] = [];
  currentPage: number = 0;
  member: Member;

  loaded = false;

  replay: Replay = new Replay();
  loading: boolean = false;
  success: boolean = false;

  error: ApiError;

  showReplayView: boolean = false;

  constructor(private commentService: CommentService,
              private authService: AuthenticationService) { }

  ngOnInit() {

    this.member = this.authService.getMember();

    this.getReplays(this.comment.id);
  }

  getReplays(commentId: string){

    this.commentService.getAllReplaysOfComment(commentId, this.currentPage, 10).subscribe(page =>{

      this.currentPage = page.currentPage;
      let items = page.content;

      items.forEach(el =>{
        this.replays.push(el);
      });


      this.loaded = true;

    }, error =>{

      this.error = error;
      this.loading = false;
    });
  }

  sendReplay(){

    this.replay.member = this.member;
    this.replay.comment = this.comment;

    this.loading = true;
    this.success = false;
    this.commentService.newReplay(this.replay).subscribe(replay =>{

      this.loading = false;
      this.success = true;
      this.replay.text = "";
      this.replays.push(replay);

    }, error =>{

      this.error = error;
      this.loading = false;
      this.success = false;
    });
  }

  replayClicked(){
    this.showReplayView = !this.showReplayView;
    this.replay.text = this.comment.member.firstName + ", ";
  }

}
