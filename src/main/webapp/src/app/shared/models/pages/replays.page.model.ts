
import {JsonObject, JsonProperty} from "json2typescript";
import {Replay} from "../replay.model";

@JsonObject
export class ReplaysPage {

  @JsonProperty("content", [Replay])
  content: Replay[] = undefined;

  @JsonProperty("number", Number)
  currentPage : number = undefined;

  @JsonProperty("totalPages", Number)
  totalPages : number = undefined;

  @JsonProperty("totalElements", Number)
  totalElements : number = undefined;

  @JsonProperty("last", Boolean)
  isLastPage : boolean = undefined;



}
