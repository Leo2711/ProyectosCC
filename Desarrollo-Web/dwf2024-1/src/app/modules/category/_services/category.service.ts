/* @author Mónica Miranda Mijangos 
  @author Eduardo Leónel Sánchez Velasco 
  Version: 2
  Fecha: 11/12/2023 */

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from '../_models/category';



@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private url = "http://localhost:8080";
  private route = "/category";

  constructor(private http: HttpClient) { }

  /**
   * Consume el endpoint de la API consultar las categorías.
  */
  getCategories() {
    return this.http.get<Category[]>(this.url + this.route);
  }

  /**
   * Consume el endpoint de la API para crear una categoría.
  */
  createCategory(category: any) {
    return this.http.post(this.url + this.route, category);
  }

  /**
   * Consume el endpoint de la API consulta una categoría.
  */
  getCategory(id: number) {
    return this.http.get<Category>(this.url + this.route + "/" + id);
  }

  /**
   * Consume el endpoint de la API para actualizar una categoría.
  */
  updateCategory(category: any, id: number) {
    return this.http.put(this.url + this.route + "/" + id, category);
  }

  /**
   * Consume el endpoint de la API para deshabilitar una categoría.
  */
  deleteCategory(id: number) {
    return this.http.delete(this.url + this.route + "/" + id);
  }

  /**
   * Consume el endpoint de la API para habilitar una categoría.
  */
  activateCategory(id: number) {
    return this.http.put(this.url + this.route + "/" + id + "/activate", null);
  }

  getActiveCategories() {
    return this.http.get<Category[]>(this.url + this.route + "/active");
  }
}
  
