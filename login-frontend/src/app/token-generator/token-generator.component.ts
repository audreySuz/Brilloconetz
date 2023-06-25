import {Component, OnInit} from '@angular/core';
import {User} from "../user";
import {TokenService} from "../token.service";
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import {ModalComponent} from "../modal/modal.component";
import {HttpErrorResponse} from "@angular/common/http";


@Component({
  selector: 'app-token-generator',
  templateUrl: './token-generator.component.html',
  styleUrls: ['./token-generator.component.css']
})
export class TokenGeneratorComponent implements OnInit {


  user: User = new User();
  maxDate = new Date();
  bsModalRef: BsModalRef;
  modalRef: BsModalRef;


  constructor(private tokenService: TokenService, private modalService: BsModalService
  ) {
    this.maxDate.setDate(this.maxDate.getDate() + 7);
  }


  onSubmit() {
    this.generateToken()
  }
  ngOnInit(): void {
  }
   generateToken() {
    this.tokenService.generateToken(this.user).subscribe(
      (data) => {
        this.openModal(data);
      },
      (error: HttpErrorResponse) => {
        console.log(error);
        this.openModalWithError(error);
      }
   );
  }

  openModal(data: any) {
    const initialState = {
      tokenData: data
    };
    this.bsModalRef = this.modalService.show(ModalComponent, {
      initialState
    });
  }

  openModalWithError(error: HttpErrorResponse) {
    const errorData = error.error.data;
    const errorMessage = error.error.message;

    const initialState = {
      errorMessage: errorMessage,
      errorData: errorData
    };
    this.modalRef = this.modalService.show(ModalComponent, { initialState });
  }

}
