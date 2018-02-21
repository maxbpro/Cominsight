import {Comment} from "../comment.model";
import {Member} from "../member.model";
import {Any, JsonObject, JsonProperty} from "json2typescript";
import {DateConverter} from "../../converters/date.converter";

@JsonObject
export class ApiSubError{

  @JsonProperty("object", String, true)
  object: string = undefined;

  @JsonProperty("field", String, true)
  field?: string = undefined;

  @JsonProperty("message", String, true)
  message?: string = undefined;

  @JsonProperty("rejectedValue", Any, true)
  rejectedValue?: any = undefined;

}
