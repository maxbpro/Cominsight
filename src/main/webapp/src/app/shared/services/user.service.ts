import { Injectable } from '@angular/core';
import {HttpParams} from "@angular/common/http";
import {User} from "../models/user.model";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs/Observable";
import {MembersPage} from "../models/pages/members.page.model";
import {BaseService} from "./core/base.service";
import {catchError} from "rxjs/operators";

@Injectable()
export class UserService extends BaseService{


  getUserById(userId: string) : Observable<User>{

    return this.http.get<User>(environment.serverEndpoint + '/api/v1/users/' + userId)
      .map(response => {

        let result = response["result"];
        return this.parseUser(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  newUser(user: User) : Observable<User>{

    return this.http.post<User>(environment.serverEndpoint + '/api/v1/users' , JSON.stringify(user),
      {headers:{'Content-Type': 'application/json'}})
      .map(response => {

        let result = response["result"];
        return this.parseUser(result);

      }).pipe(catchError(err => this.handleError(err)));
  }


  updateUser(userId: string, user: User) : Observable<User>{

    return this.http.put<User>(environment.serverEndpoint + '/api/v1/users/' + userId , JSON.stringify(user),
      {headers:{'Content-Type': 'application/json'}})
      .map(response => {

        let result = response["result"];
        return this.parseUser(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  updateAvatar(file: File) : Observable<User>{

    const formData: FormData = new FormData();
    formData.append('image', file, file.name);

    return this.http.put<User>(environment.serverEndpoint + '/api/v1/users/update/avatar' , formData)
      .map(response => {

        let result = response["result"];
        return this.parseUser(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  getAllMembersOfCompany(companyId: string, page: number, totalItems: number) : Observable<MembersPage>{

    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', totalItems.toString());

    return this.http.get<MembersPage>(environment.serverEndpoint + '/api/v1/users/company/' + companyId, { params: params })
      .map(response => {

        let result = response["result"];
        return this.parseUser(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  confirmEmail(token: string) : Observable<User>{

    return this.http.get<User>(environment.serverEndpoint + '/api/v1/registrationConfirm/' + token)
      .map(response => {

        let result = response["result"];
        return this.parseUser(result);

      }).pipe(catchError(err => this.handleError(err)));

  }

  resetPassword(email: string) : Observable<User>{

    let params = new HttpParams()
      .set('email', email);

    return this.http.get<User>(environment.serverEndpoint + '/api/v1/reset', { params: params })
      .map(response => {

        let result = response["result"];
        return this.parseUser(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  updatePassword(userId: string, token: string, password: string) : Observable<User>{

    let params = new HttpParams()
      .set('userId', userId)
      .set('token', token)
      .set('password', password);

    return this.http.get<User>(environment.serverEndpoint + '/api/v1/user/updatePassword', { params: params })
      .map(response => {

        let result = response["result"];
        return this.parseUser(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  changePassword(oldpassword: string, password: string) : Observable<User>{

    let params = new HttpParams()
      .set('old', oldpassword)
      .set('password', password);

    return this.http.get<User>(environment.serverEndpoint + '/api/v1/user/changePassword', { params: params })
      .map(response => {

        let result = response["result"];
        return this.parseUser(result);

      }).pipe(catchError(err => this.handleError(err)));
  }
}
