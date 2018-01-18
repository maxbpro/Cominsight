import { Injectable } from '@angular/core';
import {Company} from "../models/company";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class CompanyService {

  private baseUrl: string = 'http://localhost:8080/api/v1/companies';


  constructor(private http: HttpClient) { }


  getAll() {
    return this.http.get<Company[]>('/api/v1/companies');
  }

  getById(id: string) {
    return this.http.get('/api/v1/companies' + id);
  }

  create(company: Company) {
    return this.http.post('/api/v1/companies', company);
  }

  update(company: Company) {
    return this.http.put('/api/v1/companies/' + company.id, company);
  }

  delete(id: number) {
    return this.http.delete('/api/v1/companies/' + id);
  }
}
