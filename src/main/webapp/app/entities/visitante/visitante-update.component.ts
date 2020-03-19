import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVisitante, Visitante } from 'app/shared/model/visitante.model';
import { VisitanteService } from './visitante.service';

@Component({
  selector: 'jhi-visitante-update',
  templateUrl: './visitante-update.component.html'
})
export class VisitanteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    email: [],
    telefone: [],
    idCelular: [null, [Validators.required]],
    data: [null, [Validators.required]]
  });

  constructor(protected visitanteService: VisitanteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ visitante }) => {
      if (!visitante.id) {
        const today = moment().startOf('day');
        visitante.data = today;
      }

      this.updateForm(visitante);
    });
  }

  updateForm(visitante: IVisitante): void {
    this.editForm.patchValue({
      id: visitante.id,
      nome: visitante.nome,
      email: visitante.email,
      telefone: visitante.telefone,
      idCelular: visitante.idCelular,
      data: visitante.data ? visitante.data.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const visitante = this.createFromForm();
    if (visitante.id !== undefined) {
      this.subscribeToSaveResponse(this.visitanteService.update(visitante));
    } else {
      this.subscribeToSaveResponse(this.visitanteService.create(visitante));
    }
  }

  private createFromForm(): IVisitante {
    return {
      ...new Visitante(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      email: this.editForm.get(['email'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
      idCelular: this.editForm.get(['idCelular'])!.value,
      data: this.editForm.get(['data'])!.value ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVisitante>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
