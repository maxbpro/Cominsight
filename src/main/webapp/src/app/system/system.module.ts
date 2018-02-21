import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from '../shared/shared.module';
import { SystemRoutingModule } from './system-routing.module';
import {TimelineService} from "../shared/services/timeline.service";
import { CompanyPageComponent } from './company-page/company-page.component';
import { MemberPageComponent } from './member-page/member-page.component';
import { BlogPageComponent } from './blog-page/blog-page.component';
import {SystemComponent} from "./system.component";
import { HeaderComponent } from './components/header/header.component';
import { CompanyDetailsComponent } from './components/company-details/company-details.component';
import { MemberDetailsComponent } from './components/member-details/member-details.component';
import { CompanyMembersComponent } from './components/company-members/company-members.component';
import { CompanyMediaComponent } from './components/company-media/company-media.component';
import { MemberActivityComponent } from './components/member-activity/member-activity.component';
import { MemberMediaComponent } from './components/member-media/member-media.component';
import { TimelinePageComponent } from './timeline-page/timeline-page.component';
import { NewPostPageComponent } from './new-post-page/new-post-page.component';
import { PostDetailsPageComponent } from './post-details-page/post-details-page.component';
import { MemberCompaniesComponent } from './components/member-companies/member-companies.component';
import { MyProfilePageComponent } from './my-profile-page/my-profile-page.component';
import { MyCompanyPageComponent } from './my-company-page/my-company-page.component';
import { TimelineItemComponent } from './components/timeline-item/timeline-item.component';
import { MemberBlogsComponent } from './components/member-blogs/member-blogs.component';
import { CompanyBlogsComponent } from './components/company-blogs/company-blogs.component';
import { BlogItemComponent } from './components/blog-item/blog-item.component';
import { CompanyItemComponent } from './components/company-item/company-item.component';
import { UserItemComponent } from './components/user-item/user-item.component';
import { MediaItemComponent } from './components/media-item/media-item.component';
import { NewBlogPageComponent } from './new-blog-page/new-blog-page.component';
import { NoticeItemComponent } from './components/notice-item/notice-item.component';
import { AdvertisementsItemComponent } from './components/advertisements-item/advertisements-item.component';
import { BlogsSummaryItemComponent } from './components/blogs-summary-item/blogs-summary-item.component';
import { CommentItemComponent } from './components/comment-item/comment-item.component';
import { CommentReplayItemComponent } from './components/comment-replay-item/comment-replay-item.component';
import {MemberService} from "../shared/services/member.service";
import {CompanyService} from "../shared/services/company.service";
import { CompanyFollowingItemComponent } from './components/company-following-item/company-following-item.component';
import {Ng2FileInputModule} from "ng2-file-input";
import { ChangePasswordPageComponent } from './change-password-page/change-password-page.component';


@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    SystemRoutingModule,
    Ng2FileInputModule.forRoot({
      dropText:"Drop file here or Browse it",
      browseText:"Browse",
      removeText:"Remove",
      invalidFileText:"You have picked an invalid or disallowed file.",
      invalidFileTimeout:8000,
      removable:true,
      multiple:false,
      showPreviews:false,
      extensions:['jpg']
    })
  ],
  declarations: [
    SystemComponent,
    CompanyPageComponent,
    MemberPageComponent,
    BlogPageComponent,
    HeaderComponent,
    CompanyDetailsComponent,
    MemberDetailsComponent,
    CompanyMembersComponent,
    CompanyMediaComponent,
    MemberActivityComponent,
    MemberMediaComponent,
    TimelinePageComponent,
    NewPostPageComponent,
    PostDetailsPageComponent,
    MemberCompaniesComponent,
    MyProfilePageComponent,
    MyCompanyPageComponent,
    TimelineItemComponent,
    MemberBlogsComponent,
    CompanyBlogsComponent,
    BlogItemComponent,
    CompanyItemComponent,
    UserItemComponent,
    MediaItemComponent,
    NewBlogPageComponent,
    NoticeItemComponent,
    AdvertisementsItemComponent,
    BlogsSummaryItemComponent,
    CommentItemComponent,
    CommentReplayItemComponent,
    CompanyFollowingItemComponent,
    ChangePasswordPageComponent
  ],
  providers: [TimelineService, MemberService, CompanyService]
})
export class SystemModule {}
