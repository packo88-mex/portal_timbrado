import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';

import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface ITipoCfdi {
  id?: number;
  fecha?: Date | null;
  tipoCfdi?: string | null;
  estatus?: Estatus | null;
  fechaModificacion?: Date | null;
  usuario?: string | null;
  tiporecibos?: ITipoRecibo[] | null;
}

export class TipoCfdi implements ITipoCfdi {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public tipoCfdi?: string | null,
    public estatus?: Estatus | null,
    public fechaModificacion?: Date | null,
    public usuario?: string | null,
    public tiporecibos?: ITipoRecibo[] | null
  ) {}
}
