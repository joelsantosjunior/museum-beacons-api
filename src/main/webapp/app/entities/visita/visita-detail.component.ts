import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVisita } from 'app/shared/model/visita.model';

@Component({
  selector: 'jhi-visita-detail',
  templateUrl: './visita-detail.component.html'
})
export class VisitaDetailComponent implements OnInit {
  visita: IVisita | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ visita }) => (this.visita = visita));
  }

  previousState(): void {
    window.history.back();
  }
}
