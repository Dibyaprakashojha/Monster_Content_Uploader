import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ContentUploadService {
  private _getUrl = '';

  constructor(private http: HttpClient) {}

  getJobsWithSortingAndPagination(
    offset: number,
    pageSize: number,
    active?: string,
    direction?: string
  ): Observable<any[]> {
    if (active) {
      if (direction) {
        let doctorResponse$ = this.http.get<any>(
          this._getUrl.concat('page/') +
            offset +
            '/' +
            pageSize +
            '/sort/' +
            active +
            '/' +
            direction
        );
        return doctorResponse$.pipe(
          map((response) => {
            return response.content;
          })
        );
      } else {
        return this.http
          .get<any>(this._getUrl.concat('page/') + offset + '/' + pageSize)
          .pipe(
            map((response) => {
              return response.content;
            })
          );
      }
    } else {
      return this.http
        .get<any>(this._getUrl.concat('/page/') + offset + '/' + pageSize)
        .pipe(
          map((response) => {
            return response.content;
          })
        );
    }
  }
}
