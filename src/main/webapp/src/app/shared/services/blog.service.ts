import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs/Observable";
import {Blog} from "../models/blog.model";
import {BlogsPage} from "../models/pages/blogs.page.model";
import {catchError} from "rxjs/operators";
import {BaseService} from "./core/base.service";

@Injectable()
export class BlogService extends BaseService{


  getById(id: string) : Observable<Blog>{
    return this.http.get(environment.serverEndpoint + '/api/v1/blogs/' + id).map(response =>{
      let result = response["result"];
      return this.parseBlogsPage(result);
    }).pipe(catchError(err => this.handleError(err)));
  }


  update(blog: Blog) : Observable<Blog>{
    return this.http.put(environment.serverEndpoint + '/api/v1/blogs', blog).map(response =>{
      let result = response["result"];
      return this.parseBlogsPage(result);
    }).pipe(catchError(err => this.handleError(err)));
  }


  getAllBlogsOfCompany(companyId: string, page: number, totalItems: number) : Observable<BlogsPage>{

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', totalItems.toString());

    return this.http.get<BlogsPage>(environment.serverEndpoint + '/api/v1/blogs/company/' + companyId, { params: params })
      .map(response => {

        let result = response["result"];
        return this.parseBlogsPage(result);

    }).pipe(catchError(err => this.handleError(err)));

  }

  getAllBlogsOfMember(userId: string, page: number, totalItems: number) : Observable<BlogsPage>{

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', totalItems.toString());

    return this.http.get<BlogsPage>(environment.serverEndpoint + '/api/v1/blogs/user/' + userId, { params: params })
      .map(response => {

        let result = response["result"];
        return this.parseBlogsPage(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  newBlog(blog: Blog) : Observable<Blog>{

    return this.http.post<Blog>(environment.serverEndpoint + '/api/v1/blogs' , JSON.stringify(blog),
        {headers:{'Content-Type': 'application/json'}})
      .map(response => {

        let result = response["result"];
        return this.parseBlog(result);

      }).pipe(catchError(err => this.handleError(err)));
  }
}
