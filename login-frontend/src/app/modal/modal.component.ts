import { Component, Input } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import {Router} from "@angular/router";

@Component({
  selector: 'app-modal',
  template: `

    <div class="modal-header" *ngIf="errorData == null && verificationResponse == null &&errorMessage==null">
      <button type="button" class="close" aria-label="Close" (click)="bsModalRef.hide(); tokenData && redirectToVerifyPage()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body" *ngIf="tokenData && tokenData.data && tokenData.data.token">
      <p>{{ tokenData.message }}!!</p>
      <div class="token-wrapper" (click)="copyTokenToClipboard()">
        <p>Token:</p>
        <pre><code class="language-HTML">{{ tokenData.data.token }}</code></pre>
        <p>Expires On: {{tokenData.data.expiresBy}}</p>
      </div>
      <div class="copy-status" *ngIf="showCopyStatus">Copied!</div>
    </div>
    <div class="alert-success" *ngIf="verificationResponse">
      <button type="button" class="close" aria-label="Close" (click)="bsModalRef.hide()">
        <span aria-hidden="true">&times;</span>
      </button>
      <p><b>{{ verificationResponse.message }} !</b></p>
    </div>
    <div class="error-alert" *ngIf="errorData">
      <button type="button" class="close" aria-label="Close" (click)="bsModalRef.hide()">
        <span aria-hidden="true">&times;</span>
      </button>
      <p>{{ errorMessage }}!!</p>
      <p>Reasons:</p>
      <ul>
        <li *ngFor="let reason of errorData">
          {{ reason.field ? reason.field + ' - ' : '' }}{{ reason.message }}
        </li>
      </ul>
    </div>
    <div class="error-alert" *ngIf="errorMessage != null && errorData==null">
      <button type="button" class="close" aria-label="Close" (click)="bsModalRef.hide()">
        <span aria-hidden="true">&times;</span>
      </button>
      <p>{{ errorMessage }}!!</p>
    </div>
  `,
  styles: [`
    .token-wrapper {
      width: 100%;
      overflow-wrap: break-word;
      word-wrap: break-word;
      hyphens: auto;
    }
    .copy-status {
      opacity: 1;
      animation: fadeOut 2s ease-in-out;
      color: green;
    }
    .error-alert {
      position: absolute;
      top: 10px;
      left: 50%;
      transform: translateX(-50%);
      padding: 6px 12px;
      background-color: rgba(255, 0, 0, 0.8);
      color: white;
      border-radius: 4px;
    }
    .alert-success {
      position: absolute;
      top: 10px;
      left: 50%;
      transform: translateX(-50%);
      padding: 6px 12px;
      background-color: rgba(0, 255, 0, 0.8);
      color: white;
      border-radius: 4px;

    }
  `]
})

export class ModalComponent {
  @Input() tokenData: any;
  showCopyStatus = false;
  modalRef: BsModalRef;
  @Input() errorMessage: string;
  @Input() errorData: any[];
  @Input() token: string;
  @Input() verificationResponse: any;




  constructor(public bsModalRef: BsModalRef,private router: Router) {}



  copyTokenToClipboard() {
    const tokenInput = document.createElement('input');
    tokenInput.value = this.tokenData.data.token;
    document.body.appendChild(tokenInput);
    tokenInput.select();
    document.execCommand('copy')
    document.body.removeChild(tokenInput);

    this.showCopyStatus = true;
    setTimeout(() => {
      this.showCopyStatus = false;
    }, 2000);
  }

   redirectToVerifyPage(){
    this.router.navigate(['verify']);
  }


}
