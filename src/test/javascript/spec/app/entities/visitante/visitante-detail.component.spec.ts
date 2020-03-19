import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MaacBeaconApiTestModule } from '../../../test.module';
import { VisitanteDetailComponent } from 'app/entities/visitante/visitante-detail.component';
import { Visitante } from 'app/shared/model/visitante.model';

describe('Component Tests', () => {
  describe('Visitante Management Detail Component', () => {
    let comp: VisitanteDetailComponent;
    let fixture: ComponentFixture<VisitanteDetailComponent>;
    const route = ({ data: of({ visitante: new Visitante('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MaacBeaconApiTestModule],
        declarations: [VisitanteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VisitanteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VisitanteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load visitante on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.visitante).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
