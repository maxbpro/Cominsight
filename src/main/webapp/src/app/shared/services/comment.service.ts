import { Injectable } from '@angular/core';
import {HttpParams} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {environment} from "../../../environments/environment";
import {CommentsPage} from "../models/pages/comments.page.model";
import {Comment} from "../models/comment.model";
import {ReplaysPage} from "../models/pages/replays.page.model";
import {BaseService} from "./core/base.service";
import {catchError} from "rxjs/operators";
import {Replay} from "../models/replay.model";

@Injectable()
export class CommentService extends BaseService{

  getAllCommentsOfPost(postId: string, page: number, totalItems: number) : Observable<CommentsPage>{

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', totalItems.toString());

    return this.http.get<CommentsPage>(environment.serverEndpoint + '/api/v1/comments/post/' + postId, { params: params })
      .map(response => {

        let result = response["result"];
        return this.parseCommentsPage(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  getAllCommentsOfBlog(blogId: string, page: number, totalItems: number) : Observable<CommentsPage>{

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', totalItems.toString());

    return this.http.get<CommentsPage>(environment.serverEndpoint + '/api/v1/comments/blog/' + blogId, { params: params })
      .map(response => {

        let result = response["result"];
        return this.parseCommentsPage(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  getAllReplaysOfComment(commentId: string, page: number, totalItems: number) : Observable<ReplaysPage>{

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', totalItems.toString());

    return this.http.get<ReplaysPage>(environment.serverEndpoint + '/api/v1/comments/replays/' + commentId, { params: params })
      .map(response => {

        let result = response["result"];
        return this.parseReplaysPage(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  newComment(comment: Comment) : Observable<Comment>{

    let commentJson = this.serializeComment(comment);

    return this.http.post<Comment>(environment.serverEndpoint + '/api/v1/comments' , JSON.stringify(commentJson),
      {headers:{'Content-Type': 'application/json'}})
      .map(response => {

        let result = response["result"];
        return this.parseComment(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  newReplay(replay: Replay) : Observable<Replay>{

    let replayJson = this.serializeReplay(replay);

    return this.http.post<Replay>(environment.serverEndpoint + '/api/v1/comments/replay' , JSON.stringify(replayJson),
      {headers:{'Content-Type': 'application/json'}})
      .map(response => {

        let result = response["result"];
        return this.parseComment(result);

      }).pipe(catchError(err => this.handleError(err)));
  }
}
