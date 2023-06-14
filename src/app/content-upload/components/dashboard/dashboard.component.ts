import {
  BreakpointObserver,
  BreakpointState,
  Breakpoints,
} from '@angular/cdk/layout';
import { ViewChild, AfterViewInit } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { OtmmService } from 'src/app/shared/services/otmm.service';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { GlobalConfig as config } from 'src/Utils/config/config';
import Cookie from 'js-cookie';
@Component({
  selector: 'mcu-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit, AfterViewInit {
  isMenuOpen = false;
  contentMargin = 10;
  menuName: string = '';
  @ViewChild('mobileSideNav') mobileSideNav!: MatSidenav;
  // redirectUri: string = `${environment.baseUrl}ticketConsumer?redirectUri=${window.location.href}`;

  onToolbarMenuToggle() {
    this.isMenuOpen = !this.isMenuOpen;

    console.log('On toolbar toggled', this.isMenuOpen);

    if (this.isMenuOpen === false) {
      this.contentMargin = 4;
    } else {
      this.contentMargin = 10;
    }
  }

  mobileMenuOpen: boolean = false;
  isMobileMenuOpen = false;
  onMobileToolBarToggle(value: boolean) {
    this.mobileMenuOpen = !this.mobileMenuOpen;
    console.log(`mobileMenu open`, this.mobileMenuOpen);
    if (!value) {
      this.mobileSideNav.close();
      this.isMobileMenuOpen = false;
    } else if (value) {
      this.mobileSideNav.open();
      this.isMobileMenuOpen = true;
    }
  }

  constructor(
    private router: Router,
    private breakpointObserver: BreakpointObserver,
    private loginService: LoginService,
    private otmmService:OtmmService,
    private authenticationService:AuthenticationService
  ) {}

  ngAfterViewInit(): void {}

  menu!: string;
  activeMenuCompare() {
    if (this.router.url.includes('my-jobs')) {
      this.menu = 'my-jobs';
      this.menuName = 'MY JOBS';
    } else if (this.router.url.includes('all-jobs')) {
      this.menu = 'all-jobs';
      this.menuName = 'ALL JOBS';
    } else if (this.router.url.includes('my-workflows')) {
      this.menu = 'my-workflows';
      this.menuName = 'MY WORKFLOWS';
    } else if (this.router.url.includes('my-tasks')) {
      this.menu = 'my-tasks';
      this.menuName = 'MY TASKS';
    } else if (this.router.url.includes('content-manager')) {
      this.menu = 'content-manager';
      this.menuName = 'CONTENT MANAGER';
    } else if (this.router.url.includes('assets-team')) {
      this.menu = 'assets-team';
      this.menuName = 'ASSETS TEAM';
    } else if (this.router.url.includes('editor-form')) {
      this.menu = 'editor-form';
      this.menuName = 'EDIT FORM';
    }
    

  }

  checkMenu(menuName: string): boolean {
    this.activeMenuCompare();
    if (this.menu === menuName) {
      return true;
    }
    return false;
  }

  isMobile!: boolean;
  isDesktop!: boolean;
  isTablet!: boolean;
  ngOnInit(): void {

    // this.authenticationService.authenticateInOtds(config.config?.userName,config.config?.password).subscribe((authData:any)=>{
    //         //console.log('Logged in: ' + authData);
    //         if(authData['ticket']) {
    //           this.authenticationService.otdsTicket = authData['ticket'];
    //           this.authenticationService.otdsToken = authData['token'];
    //           Cookie.set('OTDSTicket', authData['ticket']);
    //           // this.appRouteService.navigateToRootFolder();
    //         }

    // })
    // this.loginService.getTest().subscribe({
    //   next: (res) => {
    //     console.log(`Hello World`, res);
    //   },
    //   error: (err) => {
    //     if (err.status == 401) {
    //       this.loginService.getLoginUri(this.redirectUri).subscribe({
    //         next: (response: any) => {
    //           console.log(`Url: `, response);
    //           window.location.href = response.url;
    //         },
    //       });
    //     }
    //     console.log(`error: `, err);
    //   },
    // });
    this.breakpointObserver
      .observe([Breakpoints.XSmall])
      .subscribe((result) => {
        console.log('xsmall', result);
        if (result.matches) {
          this.isMobile = true;
          this.isTablet = false;
          this.isDesktop = false;
          console.log(`isMobile`, this.isMobile);
        }
      });
    this.breakpointObserver.observe([Breakpoints.Web]).subscribe((result) => {
      console.log(`web`, result);
      if (result.matches) {
        this.isMobile = false;
        this.isTablet = false;
        this.isDesktop = true;
        console.log(`dskp`, this.isDesktop);
      }
    });
    this.breakpointObserver
      .observe([Breakpoints.Tablet, Breakpoints.Small])
      .subscribe((result) => {
        console.log(`tab`, result);
        if (result.matches) {
          this.isMobile = false;
          this.isTablet = true;
          this.isDesktop = false;
          console.log(`tablet`, this.isTablet);
        }
      });
  }

  navigateTO() {
    this.otmmService.postSession().subscribe((data)=>{
      console.log(data)
      this.otmmService.jSession='';
      this.otmmService.jSession=data.session_resource.session.id;
    });

 
    this.router.navigateByUrl('apps/basic-form');
    this.menuName = '';
    this.menu = '';
  }
}
