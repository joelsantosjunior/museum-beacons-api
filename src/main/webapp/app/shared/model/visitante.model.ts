import { Moment } from 'moment';

export interface IVisitante {
  id?: string;
  nome?: string;
  email?: string;
  telefone?: string;
  idCelular?: string;
  data?: Moment;
}

export class Visitante implements IVisitante {
  constructor(
    public id?: string,
    public nome?: string,
    public email?: string,
    public telefone?: string,
    public idCelular?: string,
    public data?: Moment
  ) {}
}
