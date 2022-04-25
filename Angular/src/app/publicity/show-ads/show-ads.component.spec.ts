import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowAdsComponent } from './show-ads.component';

describe('ShowAdsComponent', () => {
  let component: ShowAdsComponent;
  let fixture: ComponentFixture<ShowAdsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowAdsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowAdsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
