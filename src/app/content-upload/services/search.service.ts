import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, from, of } from 'rxjs';
import { SorlResponse } from 'src/app/shared/models/jobDetails';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  jobDetails: any[] = [
    {
      ID: '557061',
      CONTENT_TYPE: 'CONTENT_UPLOAD',
      JOB_ID: '557061',
      JOB_NAME: 'ASD',
      OTMM_FOLDER_ID: 'd01f61bfbdb153c3b6cb32c7c5c3aab1b8730af2',
      JOB_STATUS: 'DRAFT',
      ALBUM_NAME: 'ASD',
      THIRD_PARTY_APPROVAL: false,
      IS_MUSIC_NEEDED: false,
      BRAND_ID: '44',
      BRAND_NAME: '10',
      BRAND_DISPLAY_NAME: 'Monster',
      COUNTRY_ID: '16425',
      COUNTRY_NAME: 'FI',
      COUNTRY_DISPLAY_NAME: 'Finland',
      PRODUCT_LINE_ID: '1',
      PRODUCT_LINE_NAME: 'Rehab',
      PRODUCT_LINE_DISPLAY_NAME: 'Rehab',
      DEPARTMENT_ID: '4',
      DEPARTMENT_NAME: 'Sales OPS',
      DEPARTMENT_DISPLAY_NAME: 'Sales OPS',
      DEPARTMENT_CODE: '16',
      CREATED_USER_ID: 'Susan',
      CREATED_USER_IDENTITY_ID: '16483',
      CREATED_USER_NAME: 'Susan',
      CREATED_DATE: '2023-05-15T20:20:31.113+0000',
      _version_: 1765966546012733400,
    },
    {
      ID: '557061',
      CONTENT_TYPE: 'CONTENT_UPLOAD',
      JOB_ID: '557061',
      JOB_NAME: 'ASD',
      OTMM_FOLDER_ID: 'd01f61bfbdb153c3b6cb32c7c5c3aab1b8730af2',
      JOB_STATUS: 'APPROVED',
      ALBUM_NAME: 'ASD',
      THIRD_PARTY_APPROVAL: false,
      IS_MUSIC_NEEDED: false,
      BRAND_ID: '44',
      BRAND_NAME: '10',
      BRAND_DISPLAY_NAME: 'Monster',
      COUNTRY_ID: '16425',
      COUNTRY_NAME: 'FI',
      COUNTRY_DISPLAY_NAME: 'Finland',
      PRODUCT_LINE_ID: '1',
      PRODUCT_LINE_NAME: 'Rehab',
      PRODUCT_LINE_DISPLAY_NAME: 'Rehab',
      DEPARTMENT_ID: '4',
      DEPARTMENT_NAME: 'Sales OPS',
      DEPARTMENT_DISPLAY_NAME: 'Sales OPS',
      DEPARTMENT_CODE: '16',
      CREATED_USER_ID: 'Susan',
      CREATED_USER_IDENTITY_ID: '16483',
      CREATED_USER_NAME: 'Susan',
      CREATED_DATE: '2023-05-15T20:20:31.113+0000',
      _version_: 1765966546012733400,
    },
    {
      ID: '557061',
      CONTENT_TYPE: 'CONTENT_UPLOAD',
      JOB_ID: '557061',
      JOB_NAME: 'ASD',
      OTMM_FOLDER_ID: 'd01f61bfbdb153c3b6cb32c7c5c3aab1b8730af2',
      JOB_STATUS: 'INREVIEW',
      ALBUM_NAME: 'ASD',
      THIRD_PARTY_APPROVAL: false,
      IS_MUSIC_NEEDED: false,
      BRAND_ID: '44',
      BRAND_NAME: '10',
      BRAND_DISPLAY_NAME: 'Monster',
      COUNTRY_ID: '16425',
      COUNTRY_NAME: 'FI',
      COUNTRY_DISPLAY_NAME: 'Finland',
      PRODUCT_LINE_ID: '1',
      PRODUCT_LINE_NAME: 'Rehab',
      PRODUCT_LINE_DISPLAY_NAME: 'Rehab',
      DEPARTMENT_ID: '4',
      DEPARTMENT_NAME: 'Sales OPS',
      DEPARTMENT_DISPLAY_NAME: 'Sales OPS',
      DEPARTMENT_CODE: '16',
      CREATED_USER_ID: 'Susan',
      CREATED_USER_IDENTITY_ID: '16483',
      CREATED_USER_NAME: 'Susan',
      CREATED_DATE: '2023-05-15T20:20:31.113+0000',
      _version_: 1765966546012733400,
    },

    {
      ID: '540711',
      CONTENT_TYPE: 'CONTENT_UPLOAD',
      JOB_ID: '540711',
      JOB_NAME: 'hjbkbk',
      OTMM_FOLDER_ID: '7ebb1df9ddeda44da47e06ae78a2035c524eb566',
      JOB_STATUS: 'INREVIEW',
      ALBUM_NAME: 'hjbkbk',
      THIRD_PARTY_APPROVAL: false,
      IS_MUSIC_NEEDED: false,
      BRAND_ID: '44',
      BRAND_NAME: '10',
      BRAND_DISPLAY_NAME: 'Monster',
      COUNTRY_ID: '16386',
      COUNTRY_NAME: 'AF',
      COUNTRY_DISPLAY_NAME: 'Afghanistan',
      PRODUCT_LINE_ID: '1',
      PRODUCT_LINE_NAME: 'Rehab',
      PRODUCT_LINE_DISPLAY_NAME: 'Rehab',
      DEPARTMENT_ID: '1',
      DEPARTMENT_NAME: 'Graphics',
      DEPARTMENT_DISPLAY_NAME: 'Creative',
      DEPARTMENT_CODE: '10',
      CREATED_USER_ID: 'Susan',
      CREATED_USER_IDENTITY_ID: '16483',
      CREATED_USER_NAME: 'Susan',
      CREATED_DATE: '2023-05-12T17:46:01.683+0000',
      _version_: 1765684935185662000,
    },
    {
      ID: '540707',
      CONTENT_TYPE: 'CONTENT_UPLOAD',
      JOB_ID: '540707',
      JOB_NAME: 'dwdwdewdwd',
      OTMM_FOLDER_ID: '0fec0ac173c6c2d38c7779cf7c7cd8fe58a7a147',
      JOB_STATUS: 'DRAFT',
      ALBUM_NAME: 'dwdwdewdwd',
      CREDITS: 'bdubwihd',
      SAP_MATERIAL_NUMBER: '847646',
      THIRD_PARTY_APPROVAL: false,
      IS_MUSIC_NEEDED: false,
      BRAND_ID: '44',
      BRAND_NAME: '10',
      BRAND_DISPLAY_NAME: 'Monster',
      COUNTRY_ID: '16392',
      COUNTRY_NAME: 'AR',
      COUNTRY_DISPLAY_NAME: 'Argentina',
      PRODUCT_LINE_ID: '1',
      PRODUCT_LINE_NAME: 'Rehab',
      PRODUCT_LINE_DISPLAY_NAME: 'Rehab',
      DEPARTMENT_ID: '4',
      DEPARTMENT_NAME: 'Sales OPS',
      DEPARTMENT_DISPLAY_NAME: 'Sales OPS',
      DEPARTMENT_CODE: '16',
      CREATED_USER_ID: 'Susan',
      CREATED_USER_IDENTITY_ID: '16483',
      CREATED_USER_NAME: 'Susan',
      CREATED_DATE: '2023-05-11T17:43:38.227+0000',
      _version_: 1765594912886096000,
    },
    {
      ID: '540706',
      CONTENT_TYPE: 'CONTENT_UPLOAD',
      JOB_ID: '540706',
      JOB_NAME: 'b',
      OTMM_FOLDER_ID: '662b920026169d88b32faa4b0bf7259b832e0b44',
      JOB_STATUS: 'APPROVED',
      ALBUM_NAME: 'b',
      THIRD_PARTY_APPROVAL: false,
      IS_MUSIC_NEEDED: false,
      BRAND_ID: '44',
      BRAND_NAME: '10',
      BRAND_DISPLAY_NAME: 'Monster',
      COUNTRY_ID: '1',
      COUNTRY_NAME: 'US',
      COUNTRY_DISPLAY_NAME: 'USA',
      PRODUCT_LINE_ID: '1',
      PRODUCT_LINE_NAME: 'Rehab',
      PRODUCT_LINE_DISPLAY_NAME: 'Rehab',
      DEPARTMENT_ID: '1',
      DEPARTMENT_NAME: 'Graphics',
      DEPARTMENT_DISPLAY_NAME: 'Creative',
      DEPARTMENT_CODE: '10',
      CREATED_USER_ID: 'Susan',
      CREATED_USER_IDENTITY_ID: '16483',
      CREATED_USER_NAME: 'Susan',
      CREATED_DATE: '2023-05-11T12:23:27.080+0000',
      _version_: 1765574086058049500,
    },
    {
      ID: '540704',
      CONTENT_TYPE: 'CONTENT_UPLOAD',
      JOB_ID: '540704',
      JOB_NAME: 'test',
      OTMM_FOLDER_ID: 'a84555a58133cecca2c5641cef1b8089f5a6d76f',
      JOB_STATUS: 'APPROVED',
      ALBUM_NAME: 'test',
      THIRD_PARTY_APPROVAL: false,
      IS_MUSIC_NEEDED: false,
      BRAND_ID: '44',
      BRAND_NAME: '10',
      BRAND_DISPLAY_NAME: 'Monster',
      COUNTRY_ID: '1',
      COUNTRY_NAME: 'US',
      COUNTRY_DISPLAY_NAME: 'USA',
      PRODUCT_LINE_ID: '1',
      PRODUCT_LINE_NAME: 'Rehab',
      PRODUCT_LINE_DISPLAY_NAME: 'Rehab',
      DEPARTMENT_ID: '1',
      DEPARTMENT_NAME: 'Graphics',
      DEPARTMENT_DISPLAY_NAME: 'Creative',
      DEPARTMENT_CODE: '10',
      CREATED_USER_ID: 'Susan',
      CREATED_USER_IDENTITY_ID: '16483',
      CREATED_USER_NAME: 'Susan',
      CREATED_DATE: '2023-05-08T17:18:34.040+0000',
      _version_: 1765320841984213000,
    },
  ];

  base_url = 'http://localhost:8081/monster-mcu/';
  // base_url = 'http://localhost:8081/muc/api/v1/';
  constructor(private httpClient: HttpClient) {}
  jobList: any;

  getAllJobDeatils(
    pageIndex: any,
    pageSize: any,
    totalRecords: any,
    sortDirection: any
  ): Observable<SorlResponse> {
    let url = this.base_url.concat('search');
    return this.httpClient.post<SorlResponse>(url, {
      page_index: pageIndex,
      page_size: pageSize,
      total_records: totalRecords,
      sort_direction: sortDirection,
    });
  }

  getMyJobDetails(
    pageIndex: any,
    pageSize: any,
    totalRecords: any,
    sortDirection: any,
    userId: any
  ): Observable<SorlResponse> {
    let url = this.base_url.concat('my-jobs');
    return this.httpClient.post<SorlResponse>(url, {
      page_index: pageIndex,
      page_size: pageSize,
      total_records: totalRecords,
      sort_direction: sortDirection,
      user_id: userId,
    });
  }

  getAllJobsForMobile(
    pageIndex: any,
    sortType?: string
  ): Observable<SorlResponse> {
    let url = this.base_url.concat('search');

    return this.httpClient.post<SorlResponse>(url, {
      page_index: pageIndex,
      page_size: 3,
      total_records: 0,
      sort_direction: 'asc',
    });
  }

  getMyJobsForMobile(pageIndex: any, userId: number): Observable<SorlResponse> {
    let url = this.base_url.concat('my-jobs');
    return this.httpClient.post<SorlResponse>(url, {
      page_index: pageIndex,
      page_size: 3,
      total_records: 0,
      sort_direction: 'asc',
      user_id: userId,
    });
  }

  refinedjobDetails: any[] = [];
  searchByKeyWord(keyword: any): Observable<any> {
    return of(
      this.jobDetails.filter(
        (jobDetail: any) => jobDetail.COUNTRY_DISPLAY_NAME === keyword
      )
    );
  }
}
