import { Injectable } from '@angular/core';
import {Observable} from "rxjs/Observable";
import {Member} from "../models/member.model";
import {environment} from "../../../environments/environment";
import {BaseService} from "./core/base.service";
import {catchError} from "rxjs/operators";

@Injectable()
export class MemberService extends BaseService{


  getUserById(userId: string) : Observable<Member> {

    return this.http.get<Member>(environment.serverEndpoint + '/api/v1/users/' + userId)
      .map(response => {

        let result = response["result"];
        return this.parseMember(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

}
