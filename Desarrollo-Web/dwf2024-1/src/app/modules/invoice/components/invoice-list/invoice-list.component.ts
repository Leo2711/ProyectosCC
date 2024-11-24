import { Component } from '@angular/core';
import { DtoInvoiceList } from '../../_dtos/dto-invoice-list';
import { ActivatedRoute, Router } from '@angular/router';
import { InvoiceService } from '../../_services/invoice.service';
import Swal from 'sweetalert2';
import { ProductService } from 'src/app/modules/product/_services/product.service';
import { CartService } from '../../_services/cart.service';
import { LayoutService } from 'src/app/modules/layout/_service/layout.service';

@Component({
  selector: 'app-invoice-list',
  templateUrl: './invoice-list.component.html',
  styleUrls: ['./invoice-list.component.css']
})
export class InvoiceListComponent {

  rfc: any | string = "";
  
  invoices: any | DtoInvoiceList[] = [];  

  sortStatus = true;
  currentPage = 1;
  itemsPerPage = 10;

  constructor(
    private route: ActivatedRoute, // recupera parámetros de la url
    private router: Router, // redirigir a otro componente
    private invoiceService: InvoiceService, // servicio invoice de API
    private cartService: CartService,
    private layoutService: LayoutService
  ) { }

  ngOnInit() {
    this.rfc = localStorage.getItem('user_rfc');
    this.getInvoices();    
    this.cartService.getCount().subscribe(count => {
      this.layoutService.updateLayout(count);
    });  
  }

  // Función para cambiar la página
  onPageChange(pageNumber: number) {
    this.currentPage = pageNumber;
  }

  showInvoice(invoice_id: string) {
    this.router.navigate(['invoice/' + invoice_id]);
  }

  getInvoices() {
    this.invoiceService.getInvoices(this.rfc).subscribe(
      res => {
        this.invoices = res.sort((a, b) => b.invoice_id - a.invoice_id);
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "Error al cargar los datos",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }
}