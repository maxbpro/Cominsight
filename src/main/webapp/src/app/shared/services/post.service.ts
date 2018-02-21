import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {PostsPage} from "../models/pages/posts.page.model";
import {environment} from "../../../environments/environment";
import {Post} from "../models/post.model";
import {BaseService} from "./core/base.service";
import {catchError} from "rxjs/operators";

@Injectable()
export class PostService extends BaseService{


  getAllPostsOfCompany(companyId: string, page: number, totalItems: number) : Observable<PostsPage>{

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', totalItems.toString());

    return this.http.get<PostsPage>(environment.serverEndpoint + '/api/v1/posts/company/' + companyId, { params: params })
      .map(response => {

        let result = response["result"];
        return this.parsePostsPage(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  getAllPostsOfMember(userId: string, page: number, totalItems: number) : Observable<PostsPage>{

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', totalItems.toString());

    return this.http.get<PostsPage>(environment.serverEndpoint + '/api/v1/posts/user/' + userId, { params: params })
      .map(response => {

        let result = response["result"];
        return this.parsePostsPage(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  getPostById(postId: string) : Observable<Post>{

    return this.http.get<Post>(environment.serverEndpoint + '/api/v1/posts/' + postId)
      .map(response => {

        let result = response["result"];
        return this.parsePost(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  newPost(file: File, post: Post) : Observable<Post>{

    const formData: FormData = new FormData();

    formData.append('data', new Blob([JSON.stringify(post)], {
      type: "application/json"
    }));

    formData.append('image', file, file.name);

    return this.http.post<Post>(environment.serverEndpoint + '/api/v1/posts' , formData)
      .map(response => {

        let result = response["result"];
        return this.parsePost(result);

      }).pipe(catchError(err => this.handleError(err)));
  }
}
