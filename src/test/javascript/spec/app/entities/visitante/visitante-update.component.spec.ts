import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MaacBeaconApiTestModule } from '../../../test.module';
import { VisitanteUpdateComponent } from 'app/entities/visitante/visitante-update.component';
import { VisitanteService } from 'app/entities/visitante/visitante.service';
import { Visitante } from 'app/shared/model/visitante.model';

describe('Component Tests', () => {
  describe('Visitante Management Update Component', () => {
    let comp: VisitanteUpdateComponent;
    let fixture: ComponentFixture<VisitanteUpdateComponent>;
    let service: VisitanteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MaacBeaconApiTestModule],
        declarations: [VisitanteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VisitanteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VisitanteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VisitanteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Visitante('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Visitante();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
