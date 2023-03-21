import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface IEstatusCfdi {
  id?: number;
  fecha?: Date | null;
  descripcionEstatusCfdi?: string | null;
  estatus?: Estatus | null;
  fechaModificacion?: Date | null;
  usuario?: string | null;
}

export class EstatusCfdi implements IEstatusCfdi {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public descripcionEstatusCfdi?: string | null,
    public estatus?: Estatus | null,
    public fechaModificacion?: Date | null,
    public usuario?: string | null
  ) {}
}
