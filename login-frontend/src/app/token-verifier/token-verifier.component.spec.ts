import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TokenVerifierComponent } from './token-verifier.component';

describe('TokenVerifierComponent', () => {
  let component: TokenVerifierComponent;
  let fixture: ComponentFixture<TokenVerifierComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TokenVerifierComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TokenVerifierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
