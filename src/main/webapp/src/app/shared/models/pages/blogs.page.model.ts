
import {JsonObject, JsonProperty} from "json2typescript";
import {Blog} from "../blog.model";

@JsonObject
export class BlogsPage {

  @JsonProperty("content", [Blog])
  content: Blog[] = undefined;

  @JsonProperty("number", Number)
  currentPage : number = undefined;

  @JsonProperty("totalPages", Number)
  totalPages : number = undefined;

  @JsonProperty("totalElements", Number)
  totalElements : number = undefined;

  @JsonProperty("last", Boolean)
  isLastPage : boolean = undefined;



}
