
import {JsonObject, JsonProperty} from "json2typescript";
import {Blog} from "../blog.model";
import {Company} from "../company.model";

@JsonObject
export class CompaniesPage {

  @JsonProperty("content", [Company])
  content: Company[] = undefined;

  @JsonProperty("number", Number)
  currentPage : number = undefined;

  @JsonProperty("totalPages", Number)
  totalPages : number = undefined;

  @JsonProperty("totalElements", Number)
  totalElements : number = undefined;

  @JsonProperty("last", Boolean)
  isLastPage : boolean = undefined;



}
