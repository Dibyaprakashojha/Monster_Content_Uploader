import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class FormService {
  constructor(private _http: HttpClient) {}
  base_url = environment.retrieveUrl;

  getAllBrands(): Observable<any> {
    let url = `${this.base_url}brand`;
    return this._http.get(url);
  }

  getAllAssetTypes(): Observable<any> {
    let url = `${this.base_url}asset-type/`;
    return this._http.get(url);
  }

  getAllSubAssetTypes(): Observable<any> {
    let url = `${this.base_url}asset-subtype/`;
    return this._http.get(url);
  }

  getAllUseCase(): Observable<any> {
    let url = `${this.base_url}use-case/`;
    return this._http.get(url);
  }

  getJobdetailsByJobId(jobId: any): Observable<any> {
    let url = `${this.base_url}job-details/`;
    return this._http.get(url.concat(jobId));
  }

  getByBrandId(brandId: any): Observable<any> {
    let url = `${this.base_url}brand/`;
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
    let url = `${this.base_url}job-details`;
    let jobDetails = {
      albumName: job.albumName,
      assetSubType: {
        assetSubtypeId: 2,
      },
      assetType: {
        assetTypeId: 2,
      },
      brand: {
        brandId: job.brand,
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
