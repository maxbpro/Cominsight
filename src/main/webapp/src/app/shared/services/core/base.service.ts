import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {JsonConvertService} from "../json-convert.service";
import {ApiError} from "../../models/error/api-error.model";
import {ErrorObservable} from "rxjs/observable/ErrorObservable";
import {User} from "../../models/user.model";
import {Blog} from "../../models/blog.model";
import {BlogsPage} from "../../models/pages/blogs.page.model";
import {Company} from "../../models/company.model";
import {Member} from "../../models/member.model";
import {PostsPage} from "../../models/pages/posts.page.model";
import {Post} from "../../models/post.model";
import {Comment} from "../../models/comment.model";
import {Replay} from "../../models/replay.model";
import {CommentsPage} from "../../models/pages/comments.page.model";
import {ReplaysPage} from '../../models/pages/replays.page.model';

@Injectable()
export class BaseService {

  constructor(protected http: HttpClient,
              protected convertService: JsonConvertService) { }

  protected parseUser(result) : User{

    let user: User;
    try {
      user = this.convertService.converter.deserialize(result, User);
      user.init();
    } catch (e) {
      console.log((<Error>e));
    }

    return user;
  }

  protected parseMember(result) : Member{

    let member: Member;
    try {
      member = this.convertService.converter.deserialize(result, Member);
      member.init();
    } catch (e) {
      console.log((<Error>e));
    }

    return member;
  }

  protected parseCompany(result) : Company{

    let company: Company;
    try {
      company = this.convertService.converter.deserialize(result, Company);
      company.init();
    } catch (e) {
      console.log((<Error>e));
    }

    return company;
  }

  protected parseBlog(result) : Blog{

    let blog: Blog;
    try {
      blog = this.convertService.converter.deserialize(result, Blog);
    } catch (e) {
      console.log((<Error>e));
    }

    return blog;

  }

  protected parseBlogsPage(result) : BlogsPage{

    let page: BlogsPage;
    try {
      page = this.convertService.converter.deserialize(result, BlogsPage);
    } catch (e) {
      console.log((<Error>e));
    }

    return page;
  }

  protected parsePostsPage(result) : PostsPage{

    let page: PostsPage;
    try {
      page = this.convertService.converter.deserialize(result, PostsPage);
    } catch (e) {
      console.log((<Error>e));
    }

    return page;
  }

  protected parsePost(result) : Post{

    let post: Post;
    try {
      post = this.convertService.converter.deserialize(result, Post);
      post.init();
    } catch (e) {
      console.log((<Error>e));
    }

    return post;
  }

  protected parseComment(result) : Comment{

    let comment: Comment;
    try {
      comment = this.convertService.converter.deserialize(result, Comment);

    } catch (e) {
      console.log((<Error>e));
    }

    return comment;
  }

  protected parseCommentReplay(result) : Replay{

    let replay: Replay;
    try {
      replay = this.convertService.converter.deserialize(result, Replay);

    } catch (e) {
      console.log((<Error>e));
    }

    return replay;
  }

  protected parseCommentsPage(result) : CommentsPage{

    let page: CommentsPage;
    try {
      page = this.convertService.converter.deserialize(result, CommentsPage);
    } catch (e) {
      console.log((<Error>e));
    }

    return page;
  }

  protected parseReplaysPage(result) : ReplaysPage{

    let page: ReplaysPage;
    try {
      page = this.convertService.converter.deserialize(result, ReplaysPage);
    } catch (e) {
      console.log((<Error>e));
    }

    return page;
  }

  protected serializeComment(comment) : string{

    let commentJson: string;
    try {
      commentJson = this.convertService.converter.serialize(comment);
    } catch (e) {
      console.log((<Error>e));
    }

    return commentJson;
  }

  protected serializeReplay(replay: Replay) : string{

    let replayJson: string;
    try {
      replayJson = this.convertService.converter.serialize(replay);
    } catch (e) {
      console.log((<Error>e));
    }

    return replayJson;
  }


  protected handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {

      console.error(`Backend returned code ${error.status}, ` + `body was: ${error.error}`);

      let apiError: ApiError;
      try {
        apiError = this.convertService.converter.deserialize(error.error.result, ApiError);
      } catch (e) {
        console.log((<Error>e));
      }

      return new ErrorObservable(apiError);
    }

    return new ErrorObservable('Something bad happened; please try again later.');
  };
}
