import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import {environment} from "../../environments/environment";


@Injectable()
export class AuthenticationService {
  constructor(private http: HttpClient) { }

  login(username: string, password: string) {

    let options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };

    let params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);
    let body = params.toString()

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
      });
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('user');
    localStorage.removeItem('token');
  }
}
