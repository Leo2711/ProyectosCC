import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { LayoutService } from '../_service/layout.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from '../../customer/_models/customer';
import { CustomerService } from '../../customer/_services/customer.service';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-router-navbar',
  templateUrl: './router-navbar.component.html',
  styleUrls: ['./router-navbar.component.css']
})
export class RouterNavbarComponent implements OnDestroy {

  private subscription: Subscription;

  customer: any | Customer = new Customer();
  rfc: any | string = "";
  layout: number = 0;

  constructor(
    private layoutService: LayoutService,
    private route: ActivatedRoute,
    private customerService: CustomerService, // servicio customer de API
  ) { 
    this.subscription = this.layoutService.layout$.subscribe(value => {
      this.layout = value;
    });
  }

  ngOnInit() {
    this.rfc = localStorage.getItem('user_rfc');
  }

  getCustomer(){
    this.customerService.getCustomer(this.rfc).subscribe(
      (res: Customer) => {
        this.customer = res;
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo obtener el usuario",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}
