/* @author Mónica Miranda Mijangos 
  @author Eduardo Leónel Sánchez Velasco 
  Version: 5
  Fecha: 12/12/2023 */

import { Component } from '@angular/core';
import { Category } from '../../_models/category';
import { FormBuilder, Validators } from '@angular/forms';
import { CategoryService } from "../../_services/category.service";
import { LayoutService } from 'src/app/modules/layout/_service/layout.service';
import { CartService } from 'src/app/modules/invoice/_services/cart.service';

import Swal from 'sweetalert2'

declare var $: any;

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent {

  categories: Category[] = [];
  activeCategories: Category[] = [];
  categoryUpdated: number = 0;

  sortStatus = true;
  currentPage = 1;
  itemsPerPage = 10;

  form = this.formBuilder.group({
    category: ["", [Validators.required]],
    code: ["", [Validators.required]],
  });

  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private categoryService: CategoryService,
    private cartService: CartService,
    private layoutService: LayoutService
  ) { }

  ngOnInit() {
    this.getCategories();
    this.cartService.getCount().subscribe(count => {
      this.layoutService.updateLayout(count);
    });
  }

  // Función para cambiar la página
  onPageChange(pageNumber: number) {
    this.currentPage = pageNumber;
  }

  // modal

  showModalForm() {
    this.categoryUpdated = 0;
    this.form.reset();
    this.submitted = false;
    $("#modalForm").modal("show");
  }

  onSubmit() {
    // validación
    this.submitted = true;
    if (this.form.invalid) return;
    this.submitted = false;

    if (this.categoryUpdated == 0) {
      this.onSubmitCreate();
    } else {
      this.onSubmitUpdate();
    }
  }

  getCategories() {
    this.sortStatus = true;
    this.categoryService.getCategories().subscribe(
      res => {
        this.categories = res.sort((a, b) => a.category_id - b.category_id); // lista de categorías de la API
      },
      err => {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "Error al cargar los datos",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }

  onSubmitCreate() {
    this.categoryService.createCategory(this.form.value).subscribe(
      res => {
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'Categoría creada',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 2000
        });

        this.getCategories();

        $("#modalForm").modal("hide");
      },
      err => {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo crear la categoría",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }

  onSubmitUpdate() {
    this.categoryService.updateCategory(this.form.value, this.categoryUpdated).subscribe(
      res => {
        // mensaje de confirmación      
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'Categoría actualizada',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 2000
        });
        this.getCategories();

        $("#modalForm").modal("hide");

        this.categoryUpdated = 0;
      },
      err => {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo actualizr la categoría",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }

  getCategory(id: string) {
    if (!id) { return; }
    let id_category = Number(id);
    this.categoryService.getCategory(id_category).subscribe(
      res => {
        console.log(res);
        console.log(this.categories.filter(el => el.category_id == id_category));
        this.categories = [res];
      },
      err => {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se encontró la categoría",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }

  updateCategory(Category: Category) {
    this.categoryUpdated = Category.category_id;

    this.form.reset();
    this.form.controls['category'].setValue(Category.category);
    this.form.controls['code'].setValue(Category.code);

    this.submitted = false;
    $("#modalForm").modal("show");
  }

  disableCategory(id: number) {
    this.categoryService.deleteCategory(id).subscribe(
      res => {
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'Categoría deshabilitada',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 2000
        });
        this.getCategories();
      },
      err => {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo eliminar la categoría",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }

  enableCategory(id: number) {
    this.categoryService.activateCategory(id).subscribe(
      res => {
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'Categoría habilitada',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 2000
        });
        this.getCategories();
      },
      err => {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo activar la categoría",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }

  getActiveCategories() {
    this.sortStatus = false;
    this.categoryService.getActiveCategories().subscribe(
      res => {
        this.categories = res;
      },
      err => {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se encontraron categorías activas",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }

  getInactiveCategories() {
    this.sortStatus = false;
    this.categoryService.getCategories().subscribe(
      res => {
        this.categories = res.sort((a, b) => a.category_id - b.category_id); // lista de categorías de la API
        this.categories = this.categories.filter(el => el.status == 0);
      },
      err => {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se encontraron categorías inactivas",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }

  cleanSearch(inputField: HTMLInputElement) {
    inputField.value = '';
    this.getCategories();
  }
}
