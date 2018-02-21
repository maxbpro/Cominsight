import { Component } from '@angular/core';
import {CompanyService} from "./shared/services/company.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  providers: [CompanyService]
})
export class AppComponent {

}
