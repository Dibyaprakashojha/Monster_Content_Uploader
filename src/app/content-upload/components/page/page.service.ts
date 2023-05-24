import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PageService {
  constructor(private http: HttpClient) {}

  public getEmployees(pageNumber: Number, pageSize: Number): Observable<any> {
    const url = `https://reqres.in/api/users?page=${pageNumber}&per_page=${pageSize}`;

    return this.http.get<any>(url);
  }
}
