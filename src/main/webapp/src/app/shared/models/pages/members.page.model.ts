
import {JsonObject, JsonProperty} from "json2typescript";
import {Post} from "../post.model";
import {Member} from "../member.model";

@JsonObject
export class MembersPage {

  @JsonProperty("content", [Member])
  content: Member[] = undefined;

  @JsonProperty("number", Number)
  currentPage : number = undefined;

  @JsonProperty("totalPages", Number)
  totalPages : number = undefined;

  @JsonProperty("totalElements", Number)
  totalElements : number = undefined;

  @JsonProperty("last", Boolean)
  isLastPage : boolean = undefined;



}
