import { Component, OnInit } from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {ModalComponent} from "../modal/modal.component";
import {TokenService} from "../token.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";

@Component({
  selector: 'app-token-verifier',
  templateUrl: './token-verifier.component.html',
  styleUrls: ['./token-verifier.component.css']
})
export class TokenVerifierComponent implements OnInit {

  constructor(private tokenService: TokenService, private modalService: BsModalService
  ) { }

  bsModalRef: BsModalRef;
  modalRef: BsModalRef;
  token: string;

  onSubmit() {
    this.verifyToken()
  }
  ngOnInit(): void {
  }

  verifyToken() {
    this.tokenService.verifyToken(this.token).subscribe(
      (data) => {
         this.openModal(data);
        console.log(data);
      },
      (error: HttpErrorResponse) => {
        console.log(error);
        this.openModalWithError(error);
      }
    );
  }

  openModal(verificationResponse: any) {
    const initialState = {
      verificationResponse: verificationResponse
    };
    this.bsModalRef = this.modalService.show(ModalComponent, {
      initialState
    });
  }

  openModalWithError(error: HttpErrorResponse) {
    const errorMessage = error.error.message;

    const initialState = {
      errorMessage: errorMessage,
    };
    this.modalRef = this.modalService.show(ModalComponent, { initialState });
  }

}
