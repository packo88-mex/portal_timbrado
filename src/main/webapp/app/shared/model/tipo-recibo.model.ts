import { IRepresentacionGrafica } from '@/shared/model/representacion-grafica.model';
import { ITipoCfdi } from '@/shared/model/tipo-cfdi.model';

import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface ITipoRecibo {
  id?: number;
  fecha?: Date | null;
  tipoRecibo?: string | null;
  clave?: string | null;
  estatus?: Estatus | null;
  fechaModificacion?: Date | null;
  usuario?: string | null;
  representaciongraficas?: IRepresentacionGrafica[] | null;
  tipocfdi?: ITipoCfdi | null;
}

export class TipoRecibo implements ITipoRecibo {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public tipoRecibo?: string | null,
    public clave?: string | null,
    public estatus?: Estatus | null,
    public fechaModificacion?: Date | null,
    public usuario?: string | null,
    public representaciongraficas?: IRepresentacionGrafica[] | null,
    public tipocfdi?: ITipoCfdi | null
  ) {}
}
