import { Component } from '@angular/core';
import { Cart } from '../../_models/cart';
import { CartService } from '../../_services/cart.service';
import Swal from 'sweetalert2';
import { ActivatedRoute, Router } from '@angular/router';
import { InvoiceService } from '../../_services/invoice.service';
import { ProductService } from 'src/app/modules/product/_services/product.service';
import { LayoutService } from 'src/app/modules/layout/_service/layout.service';
import { DtoCartDetails } from '../../_dtos/dto-cart-details';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {

  cart: any | DtoCartDetails[] = [];
  count: any | number = 0;
  rfc: any | string = "";  
  total: number = 0;
  empty : boolean = false;

  sortStatus = true;
  currentPage = 1;
  itemsPerPage = 10;

  constructor(
    private route: ActivatedRoute, // recupera parámetros de la url
    private router: Router, // redirigir a otro componente
    private cartService: CartService, // servicio cart de API
    private invoiceService: InvoiceService,
    private productService: ProductService,
    private layoutService: LayoutService
  ) { }

  ngOnInit() {
    this.rfc = localStorage.getItem('user_rfc');
    if (this.rfc) {
      this.getCart();
    }        
  }

  // Función para cambiar la página
  onPageChange(pageNumber: number) {
    this.currentPage = pageNumber;
  }

  // addToCart -> product

  removeFromCart(item: Cart){
    this.cartService.removeFromCart(item.cart_id).subscribe(
      (res: any) => {
        this.updateStock(item.gtin, item.quantity);
        this.cart = res;
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          showConfirmButton: false,
          text: "Producto eliminado", 
          background: '#E8F8F8',         
          timer: 3000
        })
        this.getCart();
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo eliminar el producto",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  updateStock(gtin: string, quantity: number) {
    this.productService.updateProductStock(gtin, quantity).subscribe(
      (res: any) => {
        console.log("stock actualizado");
      },
      err => {        
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo actualizar el stock",
          background: '#FFEFFF',
          timer: 3000
        });
      }
    );
  }

  updateQuantity(gtin: string, quantity: number) {
    let newCart : Cart = this.cart.find((el: { gtin: string; }) => el.gtin == gtin);
    newCart.quantity = quantity;
    this.cartService.addToCart(newCart).subscribe(
      res => {        
        this.updateStock(gtin, -quantity);                        
        this.getCart();
      },
      err => {        
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo actualizar la cantidad",
          background: '#FFEFFF',
          timer: 3000
        });
      }      
    );    
  }

  getCart() {
    this.cartService.getCart(this.rfc).subscribe(
      res => {
        this.cart = res;
        this.empty = this.cart.length === 0;
        this.getTotal();        
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

  deleteCart(){
    this.cartService.deleteCart(this.rfc).subscribe(
      res => {
        this.count = 0;    
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          showConfirmButton: false,
          text: "Se vacio el carrito", 
          background: '#E8F8F8',         
          timer: 3000
        })
        this.getCart();
      },
      err=>{
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo vaciar el carrito",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  getTotal() {
    this.cartService.getCart(this.rfc).subscribe(
      res => {
        this.total = 0;
        this.count = 0;        
        res.forEach(element => {
          this.count += element.quantity;
          this.total += element.product.price * element.quantity;
        });
        this.layoutService.updateLayout(this.count);
      }
    )
  }

  generateInvoice() {
    this.removeZeros();
    this.getCart();
    this.invoiceService.generateInvoice(this.rfc).subscribe(
      res => {        
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          showConfirmButton: false,
          text: "Factura generada", 
          background: '#E8F8F8',         
          timer: 3000
        })
        this.getCart();
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo generar la factura",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  removeZeros() {
    this.cart.forEach((element: Cart) => {
      if (element.quantity == 0) {
        this.removeFromCart(element);
      }
    });
  }

  redirect(url: string[]){
    this.router.navigate(url);
  }
}

