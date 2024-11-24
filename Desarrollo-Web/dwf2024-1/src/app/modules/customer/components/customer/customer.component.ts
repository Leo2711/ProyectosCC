import { Component } from '@angular/core';
import { DtoCustomerList } from '../../_dtos/dto-customer-list';
import { Region } from '../../../region/_models/region';
import { FormBuilder, Validators } from '@angular/forms';
import { CustomerService } from '../../_services/customer.service';
import { RegionService } from '../../../region/_services/region.service';

import Swal from 'sweetalert2'; // sweetalert
import { Router } from '@angular/router';
import { CartService } from 'src/app/modules/invoice/_services/cart.service';
import { LayoutService } from 'src/app/modules/layout/_service/layout.service';

declare var $: any; // jquery

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})

export class CustomerComponent {

  customers: DtoCustomerList[] = []; // lista de clientes
  regions: Region[] = []; // lista de regiones

  sortStatus = true;
  currentPage = 1;
  itemsPerPage = 10;

  // formulario de registro
  form = this.formBuilder.group({
    name: ["", [Validators.required, Validators.pattern("^[a-zA-ZÀ-ÿ][a-zA-ZÀ-ÿ ]+$")]],
    surname: ["", [Validators.required, Validators.pattern("^[a-zA-ZÀ-ÿ][a-zA-ZÀ-ÿ ]+$")]],
    rfc: ["", [Validators.required, Validators.pattern("^[ñA-Z]{3,4}[0-9]{6}[0-9A-Z]{3}$")]],
    mail: ["", [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
    region_id: ["", [Validators.required]],
    address: ["", [Validators.required]],
  });

  submitted = false; // indica si se envió el formulario

  constructor(
    private customerService: CustomerService, // servicio customer de API
    private formBuilder: FormBuilder, // formulario
    private regionService: RegionService, // servicio region de API
    private router: Router, // redirigir a otro componente
    private cartService: CartService,
    private layoutService: LayoutService,
  ) { }

  // primera función que se ejecuta
  ngOnInit() {
    this.getCustomers();
    this.cartService.getCount().subscribe(count => {
      this.layoutService.updateLayout(count);
    });
  }

  // Función para cambiar la página
  onPageChange(pageNumber: number) {
    this.currentPage = pageNumber;
  }

  // Modal
  showModalForm() {
    this.form.reset();
    this.submitted = false;
    this.getRegions();
    $("#modalForm").modal("show");
  }

  getCustomers() {
    this.customerService.getCustomers().subscribe(
      res => {
        this.customers = res; // asigna la respuesta de la API a la lista de clientes
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
          timer: 2000
        });
      }
    );
  }

  // createCustomer
  onSubmit() {
    // valida el formulario
    this.submitted = true;
    if (this.form.invalid) return;
    this.submitted = false;

    this.customerService.createCustomer(this.form.value).subscribe(
      res => {
        // muestra mensaje de confirmación
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'El cliente ha sido registrado',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 2000
        });

        this.getCustomers(); // consulta clientes con los cambios realizados

        $("#modalForm").modal("hide"); // oculta el modal de registro
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo registrar el cliente",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }

  // UPDATE -> Customer-Image

  disableCustomer(id: number) {
    this.customerService.deleteCustomer(id).subscribe(
      res => {
        // muestra mensaje de confirmación
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'El cliente ha sido desactivado',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 2000
        });

        this.getCustomers(); // consulta clientes con los cambios realizados
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo deshabilitar el cliente",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }

  enableCustomer(id: number) {
    this.customerService.activateCustomer(id).subscribe(
      res => {
        // muestra mensaje de confirmación
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'El cliente ha sido activado',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 2000
        });

        this.getCustomers(); // consulta clientes con los cambios realizados
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo habilitar alcliente",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }  

  getCustomer(rfc: string) {
    if (!rfc) { return; }
    this.customerService.getCustomer(rfc).subscribe(
      res => {
        console.log(res);
        console.log(this.customers.filter(el => el.rfc == rfc));
        this.customers = [res];        
      },
      err => {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se encontró al cliente",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }

  // Auxiliars

  showCustomer(rfc: string) {
    this.router.navigate(['customer/' + rfc]);
  }

  getRegions() {
    this.regionService.getRegions().subscribe(
      res => {
        this.regions = res; // asigna la respuesta de la API a la lista de regiones
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
          timer: 2000
        });
      }
    );
  }

  getInactiveCustomers() {
    this.customerService.getCustomers().subscribe(
      res => {
        this.customers = res.filter(el => el.status == 0);
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se encontraron clientes inactivos",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }
  
  getActiveCustomers() {
    this.customerService.getCustomers().subscribe(
      res => {
        this.customers = res.filter(el => el.status == 1);
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se encontraron clientes activos",
          background: '#F8E8F8',
          timer: 2000
        });
      }
    );
  }

  cleanSearch(inputField: HTMLInputElement) {
    inputField.value = '';
    this.getCustomers();
  }

}