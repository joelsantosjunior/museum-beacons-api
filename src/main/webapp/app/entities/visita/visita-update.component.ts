import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVisita, Visita } from 'app/shared/model/visita.model';
import { VisitaService } from './visita.service';

@Component({
  selector: 'jhi-visita-update',
  templateUrl: './visita-update.component.html'
})
export class VisitaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    email: [],
    telefone: [],
    cep: [],
    endereco: [],
    bairro: [],
    complemento: [],
    idCelular: [null, [Validators.required]],
    data: [null, [Validators.required]]
  });

  constructor(protected visitaService: VisitaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ visita }) => {
      if (!visita.id) {
        const today = moment().startOf('day');
        visita.data = today;
      }

      this.updateForm(visita);
    });
  }

  updateForm(visita: IVisita): void {
    this.editForm.patchValue({
      id: visita.id,
      nome: visita.nome,
      email: visita.email,
      telefone: visita.telefone,
      cep: visita.cep,
      endereco: visita.endereco,
      bairro: visita.bairro,
      complemento: visita.complemento,
      idCelular: visita.idCelular,
      data: visita.data ? visita.data.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const visita = this.createFromForm();
    if (visita.id !== undefined) {
      this.subscribeToSaveResponse(this.visitaService.update(visita));
    } else {
      this.subscribeToSaveResponse(this.visitaService.create(visita));
    }
  }

  private createFromForm(): IVisita {
    return {
      ...new Visita(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      email: this.editForm.get(['email'])!.value,
      telefone: this.editForm.get(['telefone'])!.value,
      cep: this.editForm.get(['cep'])!.value,
      endereco: this.editForm.get(['endereco'])!.value,
      bairro: this.editForm.get(['bairro'])!.value,
      complemento: this.editForm.get(['complemento'])!.value,
      idCelular: this.editForm.get(['idCelular'])!.value,
      data: this.editForm.get(['data'])!.value ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVisita>>): void {
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
