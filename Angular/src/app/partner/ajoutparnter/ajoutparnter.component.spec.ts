import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutparnterComponent } from './ajoutparnter.component';

describe('AjoutparnterComponent', () => {
  let component: AjoutparnterComponent;
  let fixture: ComponentFixture<AjoutparnterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AjoutparnterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AjoutparnterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
