import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {TimelineService} from "../services/timeline.service";
import {Post} from "../models/post";
import {ChangeEvent} from "angular2-virtual-scroll";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styles:[`
    virtual-scroll {
      display: block;
      width: 350px;
      height: 200px;
    }

    timeline-item {
      display: block;
      width: 100%;
      height: 300px;
    }
  `]

})
export class HomeComponent implements OnInit, OnChanges {


    @Input()
    items: Post[];

    protected buffer: Post[] = [];
    protected loading: boolean;

    private itemsPerPage = 10;

    constructor(private timelineService: TimelineService) { }

    ngOnInit() {

      console.log("ngOnInit called");

      this.timelineService.getTimeline(0, this.itemsPerPage).subscribe(data =>{

        this.buffer = this.buffer.concat(data);
        this.loading = false;

      });
    }


    ngOnChanges(changes: SimpleChanges): void {


    }

    protected fetchMore(event: ChangeEvent) {
      if (event.end !== this.buffer.length)
        return;

      this.loading = true;

      var currentPage = Math.ceil(event.start / this.itemsPerPage);

      this.timelineService.getTimeline(currentPage, this.itemsPerPage).subscribe(data =>{

        this.buffer = this.buffer.concat(data);
        this.loading = false;

      });


      // this.fetchNextChunk(this.buffer.length, 10).then(chunk => {
      //   this.buffer = this.buffer.concat(chunk);
      //   this.loading = false;
      // }, () => this.loading = false);
    }

    // protected fetchNextChunk(skip: number, limit: number): Promise<ListItem[]> {
    //   return new Promise((resolve, reject) => {
    //   });
    // }
}
