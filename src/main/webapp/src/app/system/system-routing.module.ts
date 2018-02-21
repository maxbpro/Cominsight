import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SystemComponent } from './system.component';
import {MemberPageComponent} from "./member-page/member-page.component";
import {CompanyPageComponent} from "./company-page/company-page.component";
import {BlogPageComponent} from "./blog-page/blog-page.component";
import {CompanyDetailsComponent} from "./components/company-details/company-details.component";
import {CompanyMembersComponent} from "./components/company-members/company-members.component";
import {CompanyMediaComponent} from "./components/company-media/company-media.component";
import {MemberDetailsComponent} from "./components/member-details/member-details.component";
import {MemberMediaComponent} from "./components/member-media/member-media.component";
import {MemberActivityComponent} from "./components/member-activity/member-activity.component";
import {TimelinePageComponent} from "./timeline-page/timeline-page.component";
import {NewPostPageComponent} from "./new-post-page/new-post-page.component";
import {MemberCompaniesComponent} from "./components/member-companies/member-companies.component";
import {MyProfilePageComponent} from "./my-profile-page/my-profile-page.component";
import {MyCompanyPageComponent} from "./my-company-page/my-company-page.component";
import {PostDetailsPageComponent} from "./post-details-page/post-details-page.component";
import {MemberBlogsComponent} from "./components/member-blogs/member-blogs.component";
import {CompanyBlogsComponent} from "./components/company-blogs/company-blogs.component";
import {NewBlogPageComponent} from "./new-blog-page/new-blog-page.component";
import {AuthGuard} from "../shared/guards/auth.guard";
import {ChangePasswordPageComponent} from "./change-password-page/change-password-page.component";



const routes: Routes = [
  {path: '', component: SystemComponent, canActivate: [AuthGuard], children: [
    {path: '', component: TimelinePageComponent},
    {path: 'company/:id', component: CompanyPageComponent,
      children: [
        {
          path: '',redirectTo: 'details', pathMatch: 'full'
        },
        {
          path: 'details/:id',
          component: CompanyDetailsComponent
        },
        {
          path: 'members/:id',
          component: CompanyMembersComponent
        },
        {
          path: 'blogs/:id',
          component: CompanyBlogsComponent
        },
        {
          path: 'media/:id',
          component: CompanyMediaComponent
        }
      ]},

    {path: 'member/:id', component: MemberPageComponent,
      children: [
        {
          path: '',redirectTo: 'details', pathMatch: 'full'
        },
        {
          path: 'details/:id',
          component: MemberDetailsComponent
        },
        {
          path: 'media/:id',
          component: MemberMediaComponent
        },
        {
          path: 'companies/:id',
          component: MemberCompaniesComponent
        },
        {
          path: 'blogs/:id',
          component: MemberBlogsComponent
        },
        {
          path: 'activity/:id',
          component: MemberActivityComponent
        }
      ]},

    {path: 'new_post', component: NewPostPageComponent},
    {path: 'new_blog', component: NewBlogPageComponent},
    {path: 'post/:id', component: PostDetailsPageComponent},
    {path: 'blog/:id', component: BlogPageComponent},
    {path: 'profile', component: MyProfilePageComponent},
    {path: 'profile', component: MyProfilePageComponent},
    {path: 'edit_company', component: MyCompanyPageComponent},
    {path: 'change_password', component: ChangePasswordPageComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SystemRoutingModule {}
