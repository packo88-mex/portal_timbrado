import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface IRegimenFiscal {
  id?: number;
  fecha?: Date | null;
  regimenFiscal?: string | null;
  estatus?: Estatus | null;
  fechaModificacion?: Date | null;
  usuario?: string | null;
}

export class RegimenFiscal implements IRegimenFiscal {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public regimenFiscal?: string | null,
    public estatus?: Estatus | null,
    public fechaModificacion?: Date | null,
    public usuario?: string | null
  ) {}
}
