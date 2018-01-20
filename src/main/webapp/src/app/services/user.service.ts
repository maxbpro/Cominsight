import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs/Observable";

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  getById(id: string) : Observable<User>{
    return this.http.get(environment.serverEndpoint + '/api/v1/users/' + id).map(data =>{
      return data["result"];
    });
  }

  register(user: User) : Observable<User>{
    return this.http.post<User>(environment.serverEndpoint + '/api/v1/users', user).map(data =>{
      return data["result"];
    });
  }

  update(user: User) : Observable<User>{
    return this.http.put(environment.serverEndpoint + '/api/v1/users/' + user.id, user).map(data =>{
      return data["result"];
    });
  }

  getAll() : Observable<User[]> {
    return this.http.get<User[]>(environment.serverEndpoint + '/api/v1/users').map(response => {
      return response["result"];
    });
  }
}
