import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DtoProductList } from '../_dtos/dto-product-list';
import { Product } from '../_models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private url = "http://localhost:8080";
  private route = "/product";

  constructor(private http: HttpClient) { }

  getProducts() {
    return this.http.get<DtoProductList[]>(this.url + this.route);
  }

  createProduct(product: any) {
    return this.http.post(this.url + this.route, product);
  }

  getProduct(gtin: string) {
    return this.http.get<Product>(this.url + this.route + "/" + gtin);
  }

  updateProductStock(gtin: string, stock: number) {
    return this.http.put(this.url + this.route + "/" + gtin + "/stock/" + stock, null);
  }

  updateProduct(product: any, id: number) {
    return this.http.put(this.url + this.route + "/" + id, product);
  }

  deleteProduct(id: number) {
    return this.http.delete(this.url + this.route + "/" + id);
  }

  activateProduct(id: number) {
    return this.http.put(this.url + this.route + "/" + id + "/activate", null);
  }

  getActiveProducts() {
    return this.http.get<DtoProductList[]>(this.url + this.route + "/active");
  }

  getProductsByCategory(category_id: number) {
    return this.http.get<DtoProductList[]>(this.url + this.route + "/category/" + category_id);
  }
}