/* @author Mónica Miranda Mijangos 
  @author Eduardo Leónel Sánchez Velasco 
  Version: 4
  Fecha: 11/12/2023 */

import { Component } from '@angular/core';
import { Region } from '../../_models/region';
import { FormBuilder, Validators } from '@angular/forms';
import { RegionService } from '../../_services/region.service';
import Swal from 'sweetalert2'; // sweetalert
import { CartService } from 'src/app/modules/invoice/_services/cart.service';
import { LayoutService } from 'src/app/modules/layout/_service/layout.service';

declare var $: any; // jquery

@Component({
  selector: 'app-customer',
  templateUrl: './region.component.html',
  styleUrls: ['./region.component.css']
})
export class RegionComponent {

  regions: Region[] = []; // lista de regiones
  regionUpdated: number = 0; // id de la región a actualizar

  sortStatus = true;
  currentPage = 1;
  itemsPerPage = 10;

  // formulario de registro
  form = this.formBuilder.group({
    region: ["", [Validators.required]],
    code: ["", [Validators.required]],
  });

  submitted = false; // indica si se envió el formulario

  constructor(
    private formBuilder: FormBuilder, // formulario
    private regionService: RegionService, // servicio region de API
    private cartService: CartService,
    private layoutService: LayoutService
  ) { }

  ngOnInit() {
    this.getRegions();
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
    this.form.reset();
    this.regionUpdated = 0;
    this.submitted = false;
    $("#modalForm").modal("show");
  }

  onSubmit() {
    // valida el formulario
    this.submitted = true;
    if (this.form.invalid) return;
    this.submitted = false;

    // ejecuta la función crear o actualizar según corresponda
    if (this.regionUpdated == 0) {
      this.onSubmitCreate();
    } else {
      this.onSubmitUpdate();
    }
  }

  getRegions() {
    this.regionService.getRegions().subscribe(
      res => {
        this.regions = res.sort((a, b) => a.region_id - b.region_id);
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

  // create
  onSubmitCreate() {
    this.regionService.createRegion(this.form.value).subscribe(
      res => {
        // muestra mensaje de confirmación
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'La región ha sido registrada',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 3000
        });

        this.getRegions(); // consulta regiones con los cambios realizados

        $("#modalForm").modal("hide"); // oculta el modal de registro
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: 'No se pudo crear la región',
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  getRegion(id: string) {
    if (!id) { return; }
    let id_region = Number(id);
    this.regionService.getRegion(id_region).subscribe(
      res => {
        this.regions = [res];
      },
      err => {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se encontró la región",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  updateRegion(region: Region) {
    this.regionUpdated = region.region_id;

    this.form.reset();
    this.form.controls['region'].setValue(region.region);
    this.form.controls['code'].setValue(region.code);

    this.submitted = false;
    $("#modalForm").modal("show");
  }

  onSubmitUpdate() {
    this.regionService.updateRegion(this.form.value, this.regionUpdated).subscribe(
      res => {
        // muestra mensaje de confirmación
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'La región ha sido actualizada',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 3000
        });

        this.getRegions(); // consulta regiones con los cambios realizados

        $("#modalForm").modal("hide"); // oculta el modal de registro

        this.regionUpdated = 0; // resetea el id de la región que se actualiza a 0
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo actualizar la región",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  disableRegion(id: number) {
    this.regionService.deleteRegion(id).subscribe(
      res => {
        // muestra mensaje de confirmación
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'La región ha sido desactivada',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 3000
        });

        this.getRegions();
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo desactivar la región",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  enableRegion(id: number) {
    this.regionService.activateRegion(id).subscribe(
      res => {
        // muestra mensaje de confirmación
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          toast: true,
          text: 'La región ha sido activada',
          background: '#E8F8F8',
          showConfirmButton: false,
          timer: 3000
        });

        this.getRegions();
      },
      err => {
        // muestra mensaje de error
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          toast: true,
          showConfirmButton: false,
          text: "No se pudo activar la región",
          background: '#F8E8F8',
          timer: 3000
        });
      }
    );
  }

  getActiveRegions() {
    this.regionService.getActiveRegions().subscribe(
      res => {
        this.regions = res;
        if (this.regions.length == 0) {
          Swal.fire({
            position: 'top-end',
            icon: 'warning',
            toast: true,
            showConfirmButton: false,
            text: "No se encontraron regiones",
            background: 'white',
            timer: 3000
          });
        }
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

  getInactiveRegions() {
    this.sortStatus = false;
    this.regionService.getRegions().subscribe(
      res => {
        this.regions = res.sort((a, b) => a.region_id - b.region_id);
        this.regions = this.regions.filter(el => el.status == 0);
        if (this.regions.length == 0) {
          Swal.fire({
            position: 'top-end',
            icon: 'warning',
            toast: true,
            showConfirmButton: false,
            text: "No se encontraron regiones",
            background: 'white',
            timer: 3000
          });
        }
      },
      err => {
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

  cleanSearch(inputField: HTMLInputElement) {
    inputField.value = '';
    this.getRegions();
  }
}
