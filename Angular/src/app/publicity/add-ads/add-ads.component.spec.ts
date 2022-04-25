import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAdsComponent } from './add-ads.component';

describe('AddAdsComponent', () => {
  let component: AddAdsComponent;
  let fixture: ComponentFixture<AddAdsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddAdsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAdsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
