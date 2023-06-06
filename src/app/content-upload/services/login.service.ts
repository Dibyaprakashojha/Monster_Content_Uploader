import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {}

  // baseUrl: string = environment.baseUrl;

  // getLoginUri = (redirectUri: string) => {
  //   return this.http.get(`${this.baseUrl}loginUri?redirectUri=${redirectUri}`);
  // };

  // getTest = () => {
  //   return this.http.get(`${this.baseUrl}login-test`, {
  //     withCredentials: true,
  //     responseType: 'text',
  //   });
  // };
}
