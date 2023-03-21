import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface IEstados {
  id?: number;
  fecha?: Date | null;
  pais?: string | null;
  estado?: string | null;
  descripcionEstado?: string | null;
  estatus?: Estatus | null;
  fechaModificacion?: Date | null;
  usuario?: string | null;
}

export class Estados implements IEstados {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public pais?: string | null,
    public estado?: string | null,
    public descripcionEstado?: string | null,
    public estatus?: Estatus | null,
    public fechaModificacion?: Date | null,
    public usuario?: string | null
  ) {}
}
