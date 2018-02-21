import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'appMoment'
})
export class MomentPipe implements PipeTransform {

  //05 Nov 2017

  transform(value: string, formatFrom: string, formatTo: string = 'DD MMM YYYY'): string {
    return moment(value, formatFrom).format(formatTo);
  }

}
