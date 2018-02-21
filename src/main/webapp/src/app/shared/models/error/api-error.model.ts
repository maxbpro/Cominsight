import {Comment} from "../comment.model";
import {Member} from "../member.model";
import {JsonObject, JsonProperty} from "json2typescript";
import {DateConverter} from "../../converters/date.converter";
import {ApiSubError} from "./api-sub-error.model";

@JsonObject
export class ApiError {

  @JsonProperty("status", String)
  status: String = undefined;

  @JsonProperty("type", String, true)
  type?: string = "danger";

  @JsonProperty("message", String, true)
  message?: string = undefined;

  @JsonProperty("debugMessage", String, true)
  debugMessage?: string = undefined;

  @JsonProperty("timestamp", DateConverter)
  timestamp: Date = undefined;

  @JsonProperty("subErrors", [ApiSubError], true)
  subErrors: ApiSubError[] = [];


}
