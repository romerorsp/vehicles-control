import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewFieldDialogComponent } from './create-new-field-dialog.component';

describe('CreateNewFieldDialogComponent', () => {
  let component: CreateNewFieldDialogComponent;
  let fixture: ComponentFixture<CreateNewFieldDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewFieldDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewFieldDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
