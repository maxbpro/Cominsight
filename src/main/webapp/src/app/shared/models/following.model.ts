import {Company} from "./company.model";
import {JsonObject, JsonProperty} from "json2typescript";
import {DateConverter} from "../converters/date.converter";

@JsonObject
export class Following {

  @JsonProperty("companyId", String)
  companyId: String = undefined;

  @JsonProperty("companyTitle", String)
  companyTitle:String = undefined;

  @JsonProperty("companyPhotoUrl", String, true)
  companyPhotoUrl: String = undefined;

}
