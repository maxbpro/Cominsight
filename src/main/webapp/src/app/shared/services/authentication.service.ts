import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import {environment} from "../../../environments/environment";
import {User} from "../models/user.model";
import {JsonConvert, OperationMode, ValueCheckingMode} from "json2typescript";
import {Member} from "../models/member.model";
import {catchError} from "rxjs/operators";
import {ErrorObservable} from "rxjs/observable/ErrorObservable";
import {JsonConvertService} from "./json-convert.service";
import {ApiError} from "../models/error/api-error.model";
import {BaseService} from "./core/base.service";


@Injectable()
export class AuthenticationService extends BaseService{

  login(username: string, password: string) {

    let options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };

    let params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);
    let body = params.toString();

    return this.http.post<any>(environment.serverEndpoint + '/api/v1/authenticate', body, options)
      .map(response => {
        let result = response["result"];
        // login successful if there's a jwt token in the response
        if (result.token) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('user', JSON.stringify(result.user));
          localStorage.setItem('token', result.token);
        }

        return result.user;

      }).pipe(catchError(err => this.handleError(err)));
  }

  register(user: User){
    return this.http.post<User>(environment.serverEndpoint + '/api/v1/register', user).map(response =>{

      let result = response["result"];
      // login successful if there's a jwt token in the response
      if (result.token) {
        // store user details and jwt token in local storage to keep user logged in between page refreshes
        localStorage.setItem('user', JSON.stringify(result.user));
        localStorage.setItem('token', result.token);
      }

      return result.user;

    }).pipe(catchError(err => this.handleError(err)));
  }



  logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    window.localStorage.clear();
  }

  getUser() : User{

    let userString = localStorage.getItem('user');
    let userJson = JSON.parse(userString);

    let user: User;
    try {
      user = this.convertService.converter.deserialize(userJson, User);
    } catch (e) {
      console.log((<Error>e));
    }

    return user;
  }

  getMember(): Member{
    let user = this.getUser();

    let member: Member = new Member();
    member.username = user.username;
    member.id = user.id;
    member.company = user.company;
    member.avatar = user.avatar;
    member.firstName = user.firstName;
    member.lastName = user.lastName;

    return member;
  }

  setUser(user: User){
    localStorage.setItem('user', JSON.stringify(user));
  }

}
