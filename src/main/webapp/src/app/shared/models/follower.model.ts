import {Company} from "./company.model";
import {JsonObject, JsonProperty} from "json2typescript";
import {DateConverter} from "../converters/date.converter";

@JsonObject
export class Follower {

  @JsonProperty("userId", String)
  userId: String = undefined;

  @JsonProperty("userName", String)
  userName:String = undefined;

  @JsonProperty("created", DateConverter)
  created: Date = undefined;

}
