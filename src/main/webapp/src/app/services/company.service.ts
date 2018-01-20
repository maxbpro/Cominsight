import { Injectable } from '@angular/core';
import {Company} from "../models/company";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs/Observable";

@Injectable()
export class CompanyService {

  constructor(private http: HttpClient) { }


  getAll() : Observable<Company[]> {
    return this.http.get<Company[]>(environment.serverEndpoint + '/api/v1/companies').map(response => {
      return response["result"];
    });
  }

  getById(id: string) : Observable<Company>{
    return this.http.get<Company>(environment.serverEndpoint + '/api/v1/companies/' + id).map(response => {
      return response["result"];
    });
  }

  create(company: Company)  : Observable<Company>{
    return this.http.post<Company>(environment.serverEndpoint +'/api/v1/companies', company).map(response => {
      return response["result"];
    });
  }

  update(company: Company) : Observable<Company>{
    return this.http.put<Company>(environment.serverEndpoint + '/api/v1/companies/' + company.id, company).map(response => {
      return response["result"];
    });
  }

  delete(id: number) {
    return this.http.delete(environment.serverEndpoint + '/api/v1/companies/' + id);
  }
}
