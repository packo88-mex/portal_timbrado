import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface IMotivosCancelacion {
  id?: number;
  fecha?: Date | null;
  motivoCancelacion?: string | null;
  estatus?: Estatus | null;
  fechaModificacion?: Date | null;
  usuario?: string | null;
}

export class MotivosCancelacion implements IMotivosCancelacion {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public motivoCancelacion?: string | null,
    public estatus?: Estatus | null,
    public fechaModificacion?: Date | null,
    public usuario?: string | null
  ) {}
}
