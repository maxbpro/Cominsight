import {Company} from "./company.model";
import {Following} from "./following.model";
import {JsonObject, JsonProperty} from "json2typescript";
import {environment} from "../../../environments/environment";

@JsonObject
export class User {

  @JsonProperty("id", String, true)
  id: string = undefined;

  @JsonProperty("username", String)
  username: string = undefined;

  @JsonProperty("password", String, true)
  password: string = undefined;

  @JsonProperty("matchingPassword", String, true)
  matchingPassword: string = undefined;

  @JsonProperty("email", String)
  email: string = undefined;

  @JsonProperty("firstName", String, true)
  firstName: string = undefined;

  @JsonProperty("lastName", String, true)
  lastName: string = undefined;

  @JsonProperty("text", String, true)
  text: string = undefined;

  @JsonProperty("avatar", String, true)
  avatar:string = undefined;

  @JsonProperty("company", Company, true)
  company: Company = undefined;

  @JsonProperty("following", [Following], true)
  following: Following[] = [];

  init(){
    this.avatar = environment.serverEndpoint +"/img/" + this.avatar + ".jpg";
  }
}
