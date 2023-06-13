import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NotificationServiceService } from './notification-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import {  map, Observable } from 'rxjs';
import { GlobalConfig as config } from 'src/Utils/config/config';
import Cookie from 'js-cookie';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  
  otdsTicket:any;
  public jSession!:string;
  otdsToken!:any;
  currentRout!:string;
  constructor(
    private http: HttpClient,
    ) { }


  createSession(url: string) {

      let otdsTicket = "";
      if(config.config.environment !== 'local') {
        otdsTicket = this.otdsTicket;
      }
      else {
        Cookie.get("OTDSTicket");
        


      }
     
      const httpOptions = {
        headers: new HttpHeaders().set('Access-Control-Allow-Origin','*').set('Access-Control-Allow-Methods','OPTIONS,GET,POST,PUT,DELETE'),
        withCredentials: true,
        params : new HttpParams().append('OTDSTicket',otdsTicket)
      };
      return this.http.get(url + config.config.version+"/sessions",httpOptions)
          .pipe(map((response:any) => {
            console.log('jsession'+localStorage.getItem('jsession'));
            this.jSession = response.session_resource.session.id;
            return response;
          })
      );
     
  }


 
  getOTDSTicketByUserDetails(username:any, password:any, targetResourceId:any){
    let url = config.config.otmmHost + config.config.otdsRestUrl +"/authentication/credentials";
    let data = {
      userName: username,
      password,
      targetResourceId
    };
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }
    return this.http.post(url, data, httpOptions)
  }


 authenticateInOtds = (username:any, password:any) => {
  return new Observable(observer=> {
    //let resourceId = config.config.resourceId? config.config.resourceId : "";
    this.getOTDSTicketByUserDetails(username, password, undefined).subscribe({
      next:(response) => {

      observer.next(response);
      observer.complete();
    },
    error:(error)=>{
      observer.next(error);
      observer.complete();
    }});
  });
     
   
  }


  authenticate() {
    return new Observable(observer => {
      let url = config.config.otmmHost;
      if(config.config.environment !== 'local') {
          this.http.get(url+ config.config.otdsRestUrl +'/authentication/headers',{withCredentials : true})
            .subscribe({
              next:(response:any) => {
                //console.log("getOTDSTicket");
                this.otdsTicket = response['ticket'];
                this.createSession(url).subscribe({
                  next:(sessionResponse) =>{
                  //console.log('OTMM Session initialized');
                  //this.loaderService.hide();
                  observer.next(true);
                  observer.complete();
                },
                error:(error) => {
                  observer.next(false);
                  observer.error(new Error('Error while Creating OTMM session'+error));
                }})
            }, error:(error) => {
                //console.log("Headers fails");
                observer.next(false);
                observer.error(error);
                //observer.complete();
                //this.appRouteService.navigateToOtdsLogin();
            }});
        }
        else {
          this.createSession(url).subscribe({
            next:(sessionResponse:any) =>{
            //console.log('OTMM Session initialized');
            observer.next(true);
            observer.complete();
          },
          error:(error:any) => {
            //console.log("Failed to initialize OTMM session")
            observer.next(false);
            observer.error(new Error('Error while Creating OTMM session'+error));
          }})
        }
    })
  }


    
}
