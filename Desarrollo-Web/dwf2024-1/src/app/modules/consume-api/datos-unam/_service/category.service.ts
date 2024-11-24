import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  categoryUrl = 'http://localhost:8080/category';

  constructor(private http: HttpClient) { }

  getCategoryService() {
    return this.http.get(this.categoryUrl).pipe(catchError(this.manejaError('getCategory')));
  }

  private manejaError<T>(nombreMetodo: string, result?: T) {
    return (error: any): Observable<T> => {
      console.log(`La llamada del metodo ${nombreMetodo} fracasó`);
      return of(result as T);
    }
  }

 
}