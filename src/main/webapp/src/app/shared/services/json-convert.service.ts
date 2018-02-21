import { Injectable } from '@angular/core';
import {JsonConvert, OperationMode, ValueCheckingMode} from "json2typescript";

@Injectable()
export class JsonConvertService {

  converter: JsonConvert;

  constructor() {

    this.converter = new JsonConvert();
    this.converter.operationMode = OperationMode.LOGGING; // print some debug data
    this.converter.ignorePrimitiveChecks = false; // don't allow assigning number to string etc.
    this.converter.valueCheckingMode = ValueCheckingMode.DISALLOW_NULL; // never allow null

  }

}
