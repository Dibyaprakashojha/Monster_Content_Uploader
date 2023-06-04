import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FormService {
  constructor(private _http: HttpClient) {}

  getAllBrands(): Observable<any> {
    let url = '/api/v1/brand';
    return this._http.get(url);
  }

  getByBrandId(brandId: any): Observable<any> {
    let url = '/api/v1/brand/';
    return this._http.get(url.concat(brandId));
  }

  createJob(job: any): Observable<any> {
    let brandId;
    this.getAllBrands().subscribe((brands) => {
      brands.map((brand: any) => {
        if (brand.brandName === job.brand) {
          brandId = brand.brandId;
        }
      });
    });
    console.log(job);
    let url = 'api/v1/job-details';
    let jobDetails = {
      albumName: job.albumName,
      assetSubType: {
        assetSubtypeId: 2,
      },
      assetType: {
        assetTypeId: 2,
      },
      brand: {
        brandId: 1,
      },
      businessId: null,
      comments: null,
      country: {
        countryId: job.country,
      },
      createdBy: {
        psDetailsId: 2,
      },
      createdDate: new Date(),
      department: {
        dptId: job.department,
      },
      eventDateTime: new Date(),
      jobName: null,
      jobStatus: {
        jobStatusId: 1,
      },
      lastModifedBy: {
        psDetailsId: 1,
      },
      lastModifiedDate: new Date(),
      productLine: {
        prdLineId: job.productLine,
      },
      sapMaterialNumber: null,
      useCase: {
        useCaseId: 1,
      },
    };
    console.log(`jopb`, jobDetails);
    return this._http.post(url, jobDetails, { responseType: 'text' });
  }
}
