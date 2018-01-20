export class Company {

  id: string;
  title: string;
  email: string;

  constructor(obj?: any) {
    this.id = obj && obj.id || "";
    this.title = obj && obj.title || "";
    this.email = obj && obj.email || "";
  }


}
