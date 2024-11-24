import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DtoCustomerList } from '../_dtos/dto-customer-list';
import { Customer } from '../_models/customer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private url = "http://localhost:8080";
  private route = "/customer";

  constructor(private http: HttpClient) { }

  getCustomers() {
    return this.http.get<DtoCustomerList[]>(this.url + this.route);
  }

  createCustomer(customer: any) {
    return this.http.post(this.url + this.route, customer);
  }

  updateCustomer(customer: any, id: number) {
    return this.http.put(this.url + this.route + "/" + id, customer);
  }

  deleteCustomer(id: number) {
    return this.http.delete(this.url + this.route + "/" + id);
  }

  activateCustomer(id: number) {
    return this.http.put(this.url + this.route + "/" + id + "/activate", null);
  }

  getCustomer(rfc: string) {
    return this.http.get<Customer>(this.url + this.route + "/" + rfc);
  }
}