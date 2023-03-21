import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import RegimenFiscalService from './regimen-fiscal/regimen-fiscal.service';
import EstadosService from './estados/estados.service';
import EstatusCfdiService from './estatus-cfdi/estatus-cfdi.service';
import MotivosCancelacionService from './motivos-cancelacion/motivos-cancelacion.service';
import TipoCfdiService from './tipo-cfdi/tipo-cfdi.service';
import TipoReciboService from './tipo-recibo/tipo-recibo.service';
import RepresentacionGraficaService from './representacion-grafica/representacion-grafica.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('regimenFiscalService') private regimenFiscalService = () => new RegimenFiscalService();
  @Provide('estadosService') private estadosService = () => new EstadosService();
  @Provide('estatusCfdiService') private estatusCfdiService = () => new EstatusCfdiService();
  @Provide('motivosCancelacionService') private motivosCancelacionService = () => new MotivosCancelacionService();
  @Provide('tipoCfdiService') private tipoCfdiService = () => new TipoCfdiService();
  @Provide('tipoReciboService') private tipoReciboService = () => new TipoReciboService();
  @Provide('representacionGraficaService') private representacionGraficaService = () => new RepresentacionGraficaService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
