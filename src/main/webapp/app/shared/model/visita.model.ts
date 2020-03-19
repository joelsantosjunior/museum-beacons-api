import { Moment } from 'moment';

export interface IVisita {
  id?: string;
  nome?: string;
  email?: string;
  telefone?: string;
  cep?: string;
  endereco?: string;
  bairro?: string;
  complemento?: string;
  idCelular?: string;
  data?: Moment;
}

export class Visita implements IVisita {
  constructor(
    public id?: string,
    public nome?: string,
    public email?: string,
    public telefone?: string,
    public cep?: string,
    public endereco?: string,
    public bairro?: string,
    public complemento?: string,
    public idCelular?: string,
    public data?: Moment
  ) {}
}
