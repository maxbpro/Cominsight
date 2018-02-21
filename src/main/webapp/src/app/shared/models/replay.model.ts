import {Comment} from "../../shared/models/comment.model";
import {Member} from "./member.model";
import {JsonObject, JsonProperty} from "json2typescript";
import {DateConverter} from "../converters/date.converter";

@JsonObject
export class Replay {

  @JsonProperty("id", String, true)
  id: string = undefined;

  @JsonProperty("comment", Comment)
  comment?: Comment = undefined;

  @JsonProperty("member", Member)
  member: Member = undefined;

  @JsonProperty("text", String)
  text?: string = undefined;

  @JsonProperty("created", DateConverter, true)
  created: Date = undefined;

}
