import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';

import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface IRepresentacionGrafica {
  id?: number;
  fecha?: Date | null;
  representacionGrafica?: string | null;
  estatus?: Estatus | null;
  fechaModificacion?: Date | null;
  usuario?: string | null;
  tiporecibo?: ITipoRecibo | null;
}

export class RepresentacionGrafica implements IRepresentacionGrafica {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public representacionGrafica?: string | null,
    public estatus?: Estatus | null,
    public fechaModificacion?: Date | null,
    public usuario?: string | null,
    public tiporecibo?: ITipoRecibo | null
  ) {}
}
