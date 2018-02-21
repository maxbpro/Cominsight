import {Blog} from "./blog.model";
import {Member} from "./member.model";
import {JsonObject, JsonProperty} from "json2typescript";
import {Company} from "./company.model";
import {DateConverter} from "../converters/date.converter";
import {Post} from "./post.model";

@JsonObject
export class Comment {

  @JsonProperty("id", String, true)
  id: string = undefined;

  @JsonProperty("post", Post, true)
  post?: Post = undefined;

  @JsonProperty("blog", Blog, true)
  blog: Blog = undefined;

  @JsonProperty("member", Member, true)
  member: Member = undefined;

  @JsonProperty("text", String, true)
  text?: string = undefined;

  @JsonProperty("created", DateConverter, true)
  created: Date = undefined;


}
