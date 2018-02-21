import {Company} from "./company.model";
import {Member} from "./member.model";
import {JsonObject, JsonProperty} from "json2typescript";
import {DateConverter} from "../converters/date.converter";
import {environment} from "../../../environments/environment";

@JsonObject
export class Post {


  @JsonProperty("id", String, true)
  id: String = undefined;

  @JsonProperty("company", Company)
  company:Company = undefined;

  @JsonProperty("member", Member)
  member: Member = undefined;

  @JsonProperty("photoUrl", String, true)
  photoUrl: String = undefined;

  @JsonProperty("created", DateConverter, true)
  created: Date = undefined;

  @JsonProperty("commentsCount", Number, true)
  commentsCount: number = undefined;

  @JsonProperty("tags", [String], true)
  tags: string[] = [];

  @JsonProperty("title", String)
  title: String = undefined;

  @JsonProperty("text", String)
  text: String = undefined;

  @JsonProperty("category", String)
  category: String = undefined;

  tagsString: String = "";

  init(){
    this.photoUrl = environment.serverEndpoint +"/img/" + this.photoUrl + ".jpg";
  }

}
