import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Post} from "../models/post";
import {Observable} from "rxjs/Observable";

@Injectable()
export class TimelineService {

  constructor(private http: HttpClient) { }


  getTimeline(page: number, totalItems: number) : Observable<Post[]> {

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', totalItems.toString());

    return this.http.get<Post[]>(environment.serverEndpoint + '/api/v1/timeline', { params: params })
      .map(response => {
          var result = response["result"];
          var first = result.first;
          var size = result.size;
          return result.content;
    });
  }


}
