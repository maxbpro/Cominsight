import { Injectable } from '@angular/core';
import {Company} from "../models/company.model";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs/Observable";
import {BaseService} from "./core/base.service";
import {catchError} from "rxjs/operators";

@Injectable()
export class CompanyService extends BaseService{



  search(substring: string) : Observable<Company[]>{
    return this.http.get<Company[]>(environment.serverEndpoint + '/api/v1/companies/search/' + substring).map(response => {

      let result = response["result"];

      let page: Company[];
      try {
        page = this.convertService.converter.deserializeArray(result, Company);
        //page.init();
      } catch (e) {
        console.log((<Error>e));
      }

      return page;

    }).pipe(catchError(err => this.handleError(err)));
  }

  getById(id: string) : Observable<Company>{
    return this.http.get<Company>(environment.serverEndpoint + '/api/v1/companies/' + id).map(response => {

      let result = response["result"];
      return this.parseCompany(result);

    }).pipe(catchError(err => this.handleError(err)));
  }

  startFollowing(companyId: string) : Observable<Company>{

    return this.http.post<Company>(environment.serverEndpoint + '/api/v1/companies/'
        + companyId + '/followers', {})
      .map(response => {

        let result = response["result"];
        return this.parseCompany(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  stopFollowing(companyId: string) : Observable<Company>{
    return this.http.delete<Company>(environment.serverEndpoint + '/api/v1/companies/' + companyId + '/followers')
      .map(response => {

        let result = response["result"];
        return this.parseCompany(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  newCompany(company: Company) : Observable<Company>{

    return this.http.post<Company>(environment.serverEndpoint + '/api/v1/companies' , JSON.stringify(company),
      {headers:{'Content-Type': 'application/json'}})
      .map(response => {

        let result = response["result"];
        return this.parseCompany(result);

      }).pipe(catchError(err => this.handleError(err)));
  }


  updateCompany(id: string, company: Company) : Observable<Company>{

    return this.http.put<Company>(environment.serverEndpoint + '/api/v1/companies/' + id , JSON.stringify(company),
      {headers:{'Content-Type': 'application/json'}})
      .map(response => {

        let result = response["result"];
        return this.parseCompany(result);

      }).pipe(catchError(err => this.handleError(err)));
  }

  updatePhoto(file: File) : Observable<Company>{

    const formData: FormData = new FormData();
    formData.append('image', file, file.name);

    return this.http.put<Company>(environment.serverEndpoint + '/api/v1/companies/avatar' , formData)
      .map(response => {

        let result = response["result"];
        return this.parseCompany(result);

      }).pipe(catchError(err => this.handleError(err)));
  }
}
