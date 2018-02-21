import {JsonConverter, JsonCustomConvert} from "json2typescript";
import * as moment from 'moment';

@JsonConverter
export class DateConverter implements JsonCustomConvert<Date> {
  serialize(date: Date): any {
    return moment(date).format("YYYY-MM-DDTHH:mm:ss");
  }
  deserialize(date: any): Date {
    return new Date(date);
  }
}
