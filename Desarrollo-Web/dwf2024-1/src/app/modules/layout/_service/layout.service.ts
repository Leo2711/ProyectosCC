import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LayoutService {
  private layoutSubject: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  public layout$: Observable<number> = this.layoutSubject.asObservable();

  constructor() {}

  updateLayout(newValue: number): void {    
    this.layoutSubject.next(newValue);
  }
}
