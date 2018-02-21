
import {JsonObject, JsonProperty} from "json2typescript";
import {DateConverter} from "../converters/date.converter";
import {Follower} from "./follower.model";
import {environment} from "../../../environments/environment";

@JsonObject
export class Company {

  @JsonProperty("id", String, true)
  id: string = undefined;

  @JsonProperty("photoUrl", String, true)
  photoUrl: string = undefined;

  @JsonProperty("category", String)
  category: string = undefined;

  @JsonProperty("title", String)
  title: string = undefined;

  @JsonProperty("address", String, true)
  address: string = undefined;

  @JsonProperty("text", String)
  text: string = undefined;

  @JsonProperty("email", String)
  email: string = undefined;

  @JsonProperty("url", String, true)
  url: string = undefined;

  @JsonProperty("phone", String, true)
  phone: string = undefined;

  @JsonProperty("registeredDate", DateConverter, true)
  registeredDate: Date = undefined;

  @JsonProperty("memberCount", Number)
  memberCount: number = undefined;

  @JsonProperty("coverUrl", String, true)
  coverUrl: string = undefined;

  @JsonProperty("followers", [Follower])
  followers: Follower[] = [];

  @JsonProperty("followed", Boolean, true)
  followed: boolean = undefined;

  init(){
    this.photoUrl = environment.serverEndpoint +"/img/" + this.photoUrl + ".jpg";
  }
}
