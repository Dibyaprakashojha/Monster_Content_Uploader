import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RoutingConstants } from './RoutingConstants';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { LoginService } from './content-upload/services/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'content-upload';
  redirectUri: string = `${environment.baseUrl}ticketConsumer?redirectUri=${window.location.href}`;
  constructor(private loginService: LoginService) {}
  ngOnInit() {
    //   this.loginService.getTest().subscribe({
    //     next: (res) => {
    //       console.log(`Hello World`, res);
    //     },
    //     error: (err) => {
    //       if (err.status == 401) {
    //         this.loginService.getLoginUri(this.redirectUri).subscribe({
    //           next: (response: any) => {
    //             console.log(`Url: `, response);
    //             window.location.href = response.url;
    //           },
    //         });
    //       }
    //       console.log(`error: `, err);
    //     },
    //   });
    // }
  }
}
