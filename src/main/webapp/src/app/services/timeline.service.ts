import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Post} from "../models/post";
import {Observable} from "rxjs/Observable";

@Injectable()
export class TimelineService {

  constructor(private http: HttpClient) { }


  getTimeline() : Observable<Post[]> {
    return this.http.get<Post[]>(environment.serverEndpoint + '/api/v1/timeline').map(response => {
      return response["result"];
    });
  }


}
