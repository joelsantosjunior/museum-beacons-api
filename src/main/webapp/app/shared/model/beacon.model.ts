import { TipoConteudo } from 'app/shared/model/enumerations/tipo-conteudo.model';

export interface IBeacon {
  id?: string;
  local?: string;
  idBeacon?: string;
  tipoConteudo?: TipoConteudo;
  conteudo?: string;
  legenda?: string;
}

export class Beacon implements IBeacon {
  constructor(
    public id?: string,
    public local?: string,
    public idBeacon?: string,
    public tipoConteudo?: TipoConteudo,
    public conteudo?: string,
    public legenda?: string
  ) {}
}
