import { Component, OnInit } from '@angular/core';
import {Post} from "../../shared/models/post.model";
import {TimelineService} from "../../shared/services/timeline.service";
import {environment} from "../../../environments/environment";
import {ApiError} from "../../shared/models/error/api-error.model";

@Component({
  selector: 'app-timeline-page',
  templateUrl: './timeline-page.component.html'
})
export class TimelinePageComponent implements OnInit {

  posts: Post[] = [];
  totalElements: number = 0;
  totalPages: number = 0;
  isLastPage : boolean = false;
  currentPage: number = 0;

  loaded : boolean = false;
  error: ApiError;
  loading = false;

  constructor(private timelineService: TimelineService) { }

  ngOnInit() {
    this.getPosts();
  }



  onLoadMore(){
    this.getPosts();
  }

  getPosts(){

    this.loading = true;
    this.timelineService.getTimeline(this.currentPage, 10).subscribe(page =>{

      let items = page.content;

      items.forEach(el =>{
        el.photoUrl = environment.serverEndpoint +"/img/" + el.photoUrl + ".jpg";
        this.posts.push(el);
      });

      this.loaded = true;
      this.loading = false;


    }, error => {
        this.error = error;
        this.loading = false;
      });
  }

}
