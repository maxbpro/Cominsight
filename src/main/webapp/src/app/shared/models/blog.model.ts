import {Company} from "./company.model";
import {Member} from "./member.model";
import {JsonObject, JsonProperty} from "json2typescript";
import {DateConverter} from "../converters/date.converter";

@JsonObject
export class Blog {

  @JsonProperty("id", String)
  id: string = undefined;

  @JsonProperty("company", Company)
  company?: Company = undefined;

  @JsonProperty("member", Member)
  member?: Member = undefined;

  @JsonProperty("title", String)
  title?: string = undefined;

  @JsonProperty("text", String)
  text?: string = undefined;

  @JsonProperty("tags", [String])
  tags?: string[] = [];

  @JsonProperty("commentsCount", Number)
  commentsCount?:number = 0;

  @JsonProperty("created", DateConverter)
  created: Date = undefined;

  tagsString: String = "";


}
