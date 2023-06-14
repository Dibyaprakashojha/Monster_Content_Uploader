import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, from, of } from 'rxjs';
import { SorlResponse } from 'src/app/shared/models/jobDetails';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  base_url = environment.retrieveUrl;
  // base_url = 'http://localhost:8081/mcu/api/v1/';
  constructor(private httpClient: HttpClient) {}
  jobList: any;

  getAllJobDeatils(
    pageIndex: any,
    pageSize: any,
    sortDirection: any
  ): Observable<SorlResponse> {
    let url = this.base_url.concat('all-job/search');
    return this.httpClient.post<SorlResponse>(url, {
      keyword: '*',
      cursor: {
        page_index: pageIndex,
        page_size: pageSize,
        // total_records: totalRecords,
      },
      sort: {
        sort_field: '*',
        sort_direction: sortDirection,
      },
    });
  }

  getMyJobDetails(
    pageIndex: any,
    pageSize: any,
    sortDirection: any,
    userId: any
  ): Observable<SorlResponse> {
    let url = this.base_url.concat('my-job/search');
    return this.httpClient.post<SorlResponse>(url, {
      keyword: '*',
      cursor: {
        page_index: pageIndex,
        page_size: pageSize,
        // total_records: totalRecords,
      },
      sort: {
        sort_field: '*',
        sort_direction: sortDirection,
      },
    });
  }

  getAllJobsForMobile(
    pageIndex: any,
    sortType?: string
  ): Observable<SorlResponse> {
    let url = this.base_url.concat('all-job/search');

    return this.httpClient.post<SorlResponse>(url, {
      keyword: '*',
      cursor: {
        page_index: pageIndex,
        page_size: 3,
      },
      sort: {
        sort_field: '*',
        sort_direction: sortType,
      },
    });
  }

  getMyJobsForMobile(pageIndex: any, userId: number): Observable<SorlResponse> {
    let url = this.base_url.concat('my-job/search');
    return this.httpClient.post<SorlResponse>(url, {
      keyword: '*',
      cursor: {
        page_index: pageIndex,
        page_size: 3,
        // total_records: 0,
      },
      sort: {
        sort_field: '*',
        sort_direction: 'asc',
      },
    });
  }

  refinedjobDetails: any[] = [];
}
