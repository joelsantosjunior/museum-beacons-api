import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVisitante } from 'app/shared/model/visitante.model';
import { VisitanteService } from './visitante.service';

@Component({
  templateUrl: './visitante-delete-dialog.component.html'
})
export class VisitanteDeleteDialogComponent {
  visitante?: IVisitante;

  constructor(protected visitanteService: VisitanteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.visitanteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('visitanteListModification');
      this.activeModal.close();
    });
  }
}
