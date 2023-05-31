import { Pipe, PipeTransform } from '@angular/core';
import { UtcToLocalFormat } from '../models/utc-to-local-format';

@Pipe({
  name: 'uTCToLocalPipe',
})
export class UTCToLocalPipePipe implements PipeTransform {
  transform(utcDate: string, format: UtcToLocalFormat | string): string {
    var browserLanguage = navigator.language;
    if (format === UtcToLocalFormat.SHORT) {
      let date = new Date(utcDate).toLocaleDateString(browserLanguage);
      let time = new Date(utcDate).toLocaleTimeString(browserLanguage);
      return `${date}`;
    } else if (format === UtcToLocalFormat.FULL) {
      return new Date(utcDate).toLocaleDateString(browserLanguage);
    } else {
      console.error(`Do not have logic to format utc date , format:${format}`);
    }
    return new Date(utcDate).toString();
  }
}
