import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../_models/product';
import { ProductService } from '../../_services/product.service';

import Swal from 'sweetalert2'; // sweetalert
import { FormBuilder, Validators } from '@angular/forms';
import { Category } from '../../../category/_models/category';
import { CategoryService } from '../../../category/_services/category.service';
import { NgxPhotoEditorService } from 'ngx-photo-editor';
import { ProductImageService } from '../../_services/product-image.service';
import { ProductImage } from '../../_models/product-image';
import { CartService } from 'src/app/modules/invoice/_services/cart.service';
import { LayoutService } from 'src/app/modules/layout/_service/layout.service';
import { Cart } from 'src/app/modules/invoice/_models/cart';
import { DtoProductList } from '../../_dtos/dto-product-list';

declare var $: any; // jquery

@Component({
  selector: 'app-product-image',
  templateUrl: './product-image.component.html',
  styleUrls: ['./product-image.component.css']
})
export class ProductImageComponent {

  product: any | Product = new Product(); // cliente consultado
  gtin: any | string = ""; // gtin del producto consultado

  productImgs: ProductImage[] = [];
  categories: Category[] = []; // lista de categorías
  category: any | Category = new Category(); // datos de la categoría del producto
  cart: any | Cart[] = [];
  rfc: any | string = "";

  // formulario de actualización
  form = this.formBuilder.group({
    product: ["", [Validators.required]],
    gtin: ["", [Validators.required, Validators.pattern('^[0-9]{13}$')]],
    description: ["", [Validators.required]],
    price: ["", [Validators.required, Validators.pattern('^[0-9]*$')]],
    stock: ["", [Validators.required, Validators.pattern('^[0-9]*$')]],
    category_id: ["", [Validators.required]],
  });

  submitted = false; // indica si se envió el formulario

  constructor(
    private productService: ProductService, // servicio product de API
    private productImageService: ProductImageService, // servicio product image de API
    private formBuilder: FormBuilder, // formulario
    private categoryService: CategoryService, // servicio category de API
    private route: ActivatedRoute, // recupera parámetros de la url
    private router: Router, // redirigir a otro componente
    private cartService: CartService,
    private layoutService: LayoutService,
    private service: NgxPhotoEditorService
  ) { }

  ngOnInit() {
    this.rfc = localStorage.getItem('user_rfc');
    this.gtin = this.route.snapshot.paramMap.get('gtin');
    if (this.gtin) {
      this.getProduct();
    } else {
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        toast: true,
        showConfirmButton: false,
        text: 'Id de producto inválido',
        background: '#F8E8F8',
        timer: 3000
      });
    }
    this.cartService.getCount().subscribe(count => {
      this.layoutService.updateLayout(count);
    });
  }

  // CRUD product

  getProductImages(id: number) {    
    this.productImageService.getProductImages(id).subscribe(
      (res: ProductImage[]) => {
        this.productImgs = res;
        console.log("Imgs: ",this.productImgs);
      }
    );
  }

  getProduct() {
    this.productService.getProduct(this.gtin).subscribe(
      res => {
        this.product = res; // asigna la respuesta de la API a la variable de cliente
        this.getCategory(this.product.category_id);
        this.getProductImages(this.product.product_id);
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se encontró el producto",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  addToCart(product: any, quantity: number = 1) {
    if (product.status == 0) {
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        toast: true,
        showConfirmButton: false,
        text: "Producto desactivado",
        background: '#F8E8F8',
        timer: 3000
      });
      return;
    }
    let newCart: Cart = new Cart();
    newCart.cart_id = product.id;
    newCart.gtin = product.gtin;
    newCart.quantity = quantity;
    newCart.rfc = this.rfc;
    newCart.status = product.status;
    this.cartService.addToCart(newCart).subscribe(
      (res: any) => {
        this.cart = res;
        this.updateStock(product, -quantity);
        this.updateCount(quantity);
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo añadir al carrito",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  updateStock(product: any, quantity: any) {
    this.productService.updateProductStock(product.gtin, quantity).subscribe(
      (res: any) => {
        console.log("stock actualizado");
        this.getProduct();
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

  updateCount(quantity: number) {
    this.cartService.updateCount(quantity);
    this.cartService.getCount().subscribe(count => {
      this.layoutService.updateLayout(count);
    });
  }

  onSubmit() {
    // valida el formulario
    this.submitted = true;
    if (this.form.invalid) return;
    this.submitted = false;

    this.productService.updateProduct(this.form.value, this.product.product_id).subscribe(
      res => {
        // muestra mensaje de confirmación
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'El producto ha sido actualizado',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 3000
        });

        if (this.form.controls['gtin'].value != this.gtin) {
          this.gtin = this.form.controls['gtin'].value!; // actualizamos el gtin

          // sustituimos en la url el nuevo gtin
          let currentUrl = this.router.url.split("/");
          currentUrl.pop();
          currentUrl.push(this.gtin);

          // actualizamos la url con el nuevo rfc
          this.redirect(currentUrl);
        }

        this.getProduct(); // consulta el cliente con los cambios realizados

        $("#modalForm").modal("hide"); // oculta el modal de registro
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo actualizar el producto",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  updateProduct() {
    this.form.reset();
    this.submitted = false;
    this.getCategories();

    this.form.controls['product'].setValue(this.product.product);    
    this.form.controls['gtin'].setValue(this.product.gtin);
    this.form.controls['description'].setValue(this.product.description);
    this.form.controls['price'].setValue(this.product.price);
    this.form.controls['stock'].setValue(this.product.stock);
    this.form.controls['category_id'].setValue(this.product.category_id);    

    $("#modalForm").modal("show");
  }

  // product image

  updateProductImage(image: string) {
    let productImage: ProductImage = new ProductImage();
    productImage.product_id = this.product.product_id;
    productImage.image = image;

    this.productImageService.uploadProductImage(productImage).subscribe(
      res => {
        // muestra mensaje de confirmación
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'La imagen ha sido actualizada',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 3000
        });

        this.getProduct(); // consulta el cliente con los cambios realizados

        $("#modalForm").modal("hide"); // oculta el modal de registro
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo actualizar la imagen",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  deleteProductImage(id: number) {    
    this.productImageService.deleteProductImage(id).subscribe(
      res => {
        // muestra mensaje de confirmación
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'La imagen ha sido eliminada',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 3000
        });

        this.getProduct(); // consulta el cliente con los cambios realizados

        $("#modalForm").modal("hide"); // oculta el modal de registro
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo eliminar la imagen",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  // catalogues

  getCategories() {
    this.categoryService.getCategories().subscribe(
      res => {
        this.categories = res;
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

  // auxiliary functions

  getCategory(category_id: number) {
    this.categoryService.getCategory(category_id).subscribe(
      res => {
        this.category = res;
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo obtener la categoría",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  fileChangeHandler($event: any) {
    this.service.open($event, {
      aspectRatio: 4 / 4,
      autoCropArea: 1,
      resizeToWidth: 360,
      resizeToHeight: 360,
    }).subscribe(data => {
      this.updateProductImage(data.base64!);
    });
  }

  redirect(url: string[]) {
    this.router.navigate(url);
  }
}
