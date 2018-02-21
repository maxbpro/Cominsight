import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs/Observable";
import {PostsPage} from "../models/pages/posts.page.model";
import {BaseService} from "./core/base.service";
import {catchError} from "rxjs/operators";

@Injectable()
export class TimelineService extends BaseService{


  getTimeline(page: number, totalItems: number) : Observable<PostsPage> {

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', totalItems.toString());

    return this.http.get<PostsPage>(environment.serverEndpoint + '/api/v1/timeline', { params: params })
      .map(response => {

        let result = response["result"];
        return this.parsePostsPage(result);

      }).pipe(catchError(err => this.handleError(err)));
  }


}
