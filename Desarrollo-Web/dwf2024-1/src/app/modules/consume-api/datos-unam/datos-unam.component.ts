import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CategoryService } from './_service/category.service';

@Component({
  selector: 'app-datos-unam',
  templateUrl: './datos-unam.component.html',
  styleUrls: ['./datos-unam.component.css'],
})
export class DatosUnamComponent {
  //urlRegex = '(http?://)?([\\da-z.-]+):[\\d]{4,6}\\.([a-z.]{2,6})[/\\w .-]*/?';
  urlValid: boolean = false;
  destino: string = '';
  contenidoGet: Object | undefined | unknown;
  contenidoPost: Object | undefined;

  getRequestForm = new FormGroup(
    {
      urlDestino: new FormControl(''),
    },
    [Validators.required]
  );

  postRequestForm = new FormGroup(
    {
      jsonIn: new FormControl(''),
    },
    [Validators.required]
  );

  constructor(
    private http: HttpClient,
    private categoryService: CategoryService
  ) {}

  consumeGet() {
    var apiDestinoFormValue = this.getRequestForm.value;
    var apiDestino = apiDestinoFormValue['urlDestino'];

    this.urlValid = this.validaURL(apiDestino);

    if (!this.urlValid) {
      console.log('La URL ingresada no es valida');
      return;
    }

    this.http.get(this.destino).subscribe((datos) => {
      console.log(datos);
      this.contenidoGet = datos;
    });
  }

  consumeGetManejoErrores() {
    this.categoryService.getCategoryService().subscribe((datos) => {
      console.log(datos);
      this.contenidoGet = datos;
    });
  }

  consumePost() {
    let apiEchoPost = 'https://httpbin.org/post';

    var contenidoPostFormValue = this.postRequestForm.value;
    var contenido = contenidoPostFormValue['jsonIn'];

    if (contenido === null || contenido === undefined) {
      console.log('No se ha ingresado información');
    } else {
      try {
        let objeto: Object = JSON.parse(contenido);
        this.http.post(apiEchoPost, objeto).subscribe((datos) => {
          console.log(datos);
          this.contenidoPost = datos;
        });
      } catch (e) {
        console.log(
          'La entrada no pudo ser parseada como una cadena JSON valida'
        );
        return;
      }
    }
  }

  private validaURL(apiDestino: string | null | undefined): boolean {
    if (apiDestino === null || apiDestino === undefined) {
      console.log('El valor es nulo/undefined');
      return false;
    } else {
      this.destino = apiDestino;
      return this.destino.trim().length >= 10;
    }
  }

  get fg() {
    return this.getRequestForm.controls;
  }
}