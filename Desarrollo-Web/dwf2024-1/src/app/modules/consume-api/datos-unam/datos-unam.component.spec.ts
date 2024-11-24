import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DatosUnamComponent } from './datos-unam.component';

describe('DatosUnamComponent', () => {
  let component: DatosUnamComponent;
  let fixture: ComponentFixture<DatosUnamComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DatosUnamComponent]
    });
    fixture = TestBed.createComponent(DatosUnamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
