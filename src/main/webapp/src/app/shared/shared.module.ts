import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoaderComponent } from './components/loader/loader.component';
import { FooterComponent } from './components/footer/footer.component';
import { BackToTopComponent } from './components/back-to-top/back-to-top.component';
import {PostService} from "./services/post.service";
import { MomentPipe } from './pipes/moment.pipe';
import {MomentModule} from "angular2-moment";
import {MemberService} from "./services/member.service";
import {CompanyService} from "./services/company.service";
import {BlogService} from "./services/blog.service";
import {CommentService} from "./services/comment.service";
import {UserService} from "./services/user.service";
import {TimelineService} from "./services/timeline.service";
import {JsonConvertService} from "./services/json-convert.service";


@NgModule({
  declarations: [LoaderComponent,
    FooterComponent,
    BackToTopComponent,
    MomentPipe
  ],
  imports: [
    ReactiveFormsModule,
    FormsModule,
    MomentModule,
  ],
  exports: [
    ReactiveFormsModule,
    FormsModule,
    MomentModule,
    MomentPipe,
    LoaderComponent,
    BackToTopComponent,
    FooterComponent
  ],
  providers: [
    PostService,
    JsonConvertService,
    MemberService,
    CompanyService,
    BlogService,
    CommentService,
    UserService,
    TimelineService]
})
export class SharedModule {}
