import {Company} from "./company.model";
import {JsonObject, JsonProperty} from "json2typescript";
import {Following} from "./following.model";
import {environment} from "../../../environments/environment";

@JsonObject
export class Member {

  @JsonProperty("id", String)
  id: string = undefined;

  @JsonProperty("firstName", String)
  firstName?: string = undefined;

  @JsonProperty("lastName", String)
  lastName?: string = undefined;

  @JsonProperty("username", String)
  username?: string = undefined;

  @JsonProperty("avatar", String, true)
  avatar?:string = undefined;

  @JsonProperty("company", Company)
  company?: Company = undefined;

  @JsonProperty("text", String)
  text: string = undefined;

  @JsonProperty("following", [Following], true)
  following?: Following[] = [];

  init(){
    this.avatar = environment.serverEndpoint +"/img/" + this.avatar + ".jpg";
  }

}
