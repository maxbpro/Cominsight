
import {JsonObject, JsonProperty} from "json2typescript";
import {Post} from "../post.model";

@JsonObject
export class PostsPage {

  @JsonProperty("content", [Post])
  content: Post[] = undefined;

  @JsonProperty("number", Number)
  currentPage : number = undefined;

  @JsonProperty("totalPages", Number)
  totalPages : number = undefined;

  @JsonProperty("totalElements", Number)
  totalElements : number = undefined;

  @JsonProperty("last", Boolean)
  isLastPage : boolean = undefined;



}
