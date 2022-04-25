import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListpartnersComponent } from './listpartners.component';

describe('ListpartnersComponent', () => {
  let component: ListpartnersComponent;
  let fixture: ComponentFixture<ListpartnersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListpartnersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListpartnersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
