import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVisita } from 'app/shared/model/visita.model';
import { VisitaService } from './visita.service';

@Component({
  templateUrl: './visita-delete-dialog.component.html'
})
export class VisitaDeleteDialogComponent {
  visita?: IVisita;

  constructor(protected visitaService: VisitaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.visitaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('visitaListModification');
      this.activeModal.close();
    });
  }
}
