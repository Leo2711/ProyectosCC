import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { DtoCartDetails } from '../_dtos/dto-cart-details';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private url = "http://localhost:8080";
  private route = "/cart";
  
  private countSubject: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  count$ = this.countSubject.asObservable();

  rfc: any | string = "";

  constructor(private http: HttpClient) {
    this.rfc = localStorage.getItem('user_rfc');
   }  

  addToCart(cart: any) {
    return this.http.post(this.url + this.route, cart);
  }

  deleteCart(rfc: string) {
    return this.http.delete(this.url + this.route + "/clear/" + rfc);
  }

  /* REQUERIMIENTO 4. Implementar servicio Cart - función getCart() */
  getCart(rfc: string) {
    return this.http.get<DtoCartDetails[]>(this.url + this.route + "/" + rfc);
  }

  /* REQUERIMIENTO 4. Implementar servicio Cart - función removeFromCart() */
  removeFromCart(id: number) {
    return this.http.delete(this.url + this.route + "/" + id);
  }

  setCount() {    
    this.getCart(this.rfc).subscribe(
      res => {        
        let totalCount = 0;        
        res.forEach(element => {
          totalCount += element.quantity;          
        });      
        this.countSubject.next(totalCount);
      }
    );
  }
  
  updateCount(newValue: number): void {
    console.log(this.count$);
    this.countSubject.next(newValue);
  }

  getCount(): Observable<number> {
    this.setCount();
    return this.count$;
  }
}
