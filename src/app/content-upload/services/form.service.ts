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
        if (brand.brandName === job.brandName) {
          brandId = brand.brandId;
        }
      });
    });
    let url = 'api/v1/job-details';
    let jobDetails = {
      albumName: job.albumName,
      assetSubType: {
        assetSubtypeId: null,
      },
      assetType: {
        assetTypeId: null,
      },
      brand: {
        brandId: brandId,
      },
      businessId: null,
      comments: null,
      country: {
        countryId: job.countryId,
      },
      createdBy: {
        psDetailsId: 2,
      },
      createdDate: new Date(),
      department: {
        dptId: job.departMent,
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
        prdLineId: 2,
      },
      sapMaterialNumber: null,
      useCase: {
        useCaseId: null,
      },
    };
    console.log(`jopb`, jobDetails);
    return this._http.post(url, jobDetails);
  }
}
