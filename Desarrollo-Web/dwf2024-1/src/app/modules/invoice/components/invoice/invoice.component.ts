import { Component } from '@angular/core';
import { Invoice } from '../../_models/invoice';
import { Product } from 'src/app/modules/product/_models/product';
import { ActivatedRoute, Router } from '@angular/router';
import { InvoiceService } from '../../_services/invoice.service';
import { ProductService } from 'src/app/modules/product/_services/product.service';
import Swal from 'sweetalert2';
import { Customer } from 'src/app/modules/customer/_models/customer';
import { CartService } from '../../_services/cart.service';
import { LayoutService } from 'src/app/modules/layout/_service/layout.service';
import { Item } from '../../_models/item';
import { DtoProductList } from 'src/app/modules/product/_dtos/dto-product-list';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css']
})
export class InvoiceComponent {

  rfc: any | string = "";
  id: any | number = 0;

  invoice: Invoice = new Invoice();
  items: Item[] = [];
  customer: Customer = new Customer();
  product: DtoProductList = new DtoProductList();
  products: DtoProductList[] = [];

  constructor(
    private route: ActivatedRoute, // recupera parámetros de la url
    private router: Router, // redirigir a otro componente
    private invoiceService: InvoiceService, // servicio invoice de API
    private productService: ProductService,
    private cartService: CartService,
    private layoutService: LayoutService
  ) { }

  ngOnInit() {
    this.rfc = localStorage.getItem('user_rfc');
    this.id = this.route.snapshot.paramMap.get('id');
    this.getInvoice();

    this.cartService.getCount().subscribe(count => {
      this.layoutService.updateLayout(count);
    });
  }

  redirect(url: string[]) {
    this.router.navigate(url);
  }

  getInvoice() {
    this.invoiceService.getInvoice(this.id).subscribe(
      (res: Invoice) => {
        this.invoice = res;
        this.customer = res.customer;
        this.items = res.items;
        this.items.forEach(element => {
          this.getProduct(element.gtin);
        });
        console.log("Items:", this.items);
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

  getProduct(gtin: any) {
    this.productService.getProduct(gtin).subscribe(
      (res: DtoProductList) => {
        this.products.push(res);
      }
    );
  }

}
