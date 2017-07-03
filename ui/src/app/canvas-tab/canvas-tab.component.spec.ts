import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CanvasTabComponent } from './canvas-tab.component';

describe('CanvasTabComponent', () => {
  let component: CanvasTabComponent;
  let fixture: ComponentFixture<CanvasTabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CanvasTabComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CanvasTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
