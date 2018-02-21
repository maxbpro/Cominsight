
import {JsonObject, JsonProperty} from "json2typescript";
import {Comment} from "../comment.model";

@JsonObject
export class CommentsPage {

  @JsonProperty("content", [Comment])
  content: Comment[] = undefined;

  @JsonProperty("number", Number)
  currentPage : number = undefined;

  @JsonProperty("totalPages", Number)
  totalPages : number = undefined;

  @JsonProperty("totalElements", Number)
  totalElements : number = undefined;

  @JsonProperty("last", Boolean)
  isLastPage : boolean = undefined;



}
