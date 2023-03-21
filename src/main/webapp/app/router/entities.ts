import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const RegimenFiscal = () => import('@/entities/regimen-fiscal/regimen-fiscal.vue');
// prettier-ignore
const RegimenFiscalUpdate = () => import('@/entities/regimen-fiscal/regimen-fiscal-update.vue');
// prettier-ignore
const RegimenFiscalDetails = () => import('@/entities/regimen-fiscal/regimen-fiscal-details.vue');
// prettier-ignore
const Estados = () => import('@/entities/estados/estados.vue');
// prettier-ignore
const EstadosUpdate = () => import('@/entities/estados/estados-update.vue');
// prettier-ignore
const EstadosDetails = () => import('@/entities/estados/estados-details.vue');
// prettier-ignore
const EstatusCfdi = () => import('@/entities/estatus-cfdi/estatus-cfdi.vue');
// prettier-ignore
const EstatusCfdiUpdate = () => import('@/entities/estatus-cfdi/estatus-cfdi-update.vue');
// prettier-ignore
const EstatusCfdiDetails = () => import('@/entities/estatus-cfdi/estatus-cfdi-details.vue');
// prettier-ignore
const MotivosCancelacion = () => import('@/entities/motivos-cancelacion/motivos-cancelacion.vue');
// prettier-ignore
const MotivosCancelacionUpdate = () => import('@/entities/motivos-cancelacion/motivos-cancelacion-update.vue');
// prettier-ignore
const MotivosCancelacionDetails = () => import('@/entities/motivos-cancelacion/motivos-cancelacion-details.vue');
// prettier-ignore
const TipoCfdi = () => import('@/entities/tipo-cfdi/tipo-cfdi.vue');
// prettier-ignore
const TipoCfdiUpdate = () => import('@/entities/tipo-cfdi/tipo-cfdi-update.vue');
// prettier-ignore
const TipoCfdiDetails = () => import('@/entities/tipo-cfdi/tipo-cfdi-details.vue');
// prettier-ignore
const TipoRecibo = () => import('@/entities/tipo-recibo/tipo-recibo.vue');
// prettier-ignore
const TipoReciboUpdate = () => import('@/entities/tipo-recibo/tipo-recibo-update.vue');
// prettier-ignore
const TipoReciboDetails = () => import('@/entities/tipo-recibo/tipo-recibo-details.vue');
// prettier-ignore
const RepresentacionGrafica = () => import('@/entities/representacion-grafica/representacion-grafica.vue');
// prettier-ignore
const RepresentacionGraficaUpdate = () => import('@/entities/representacion-grafica/representacion-grafica-update.vue');
// prettier-ignore
const RepresentacionGraficaDetails = () => import('@/entities/representacion-grafica/representacion-grafica-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'regimen-fiscal',
      name: 'RegimenFiscal',
      component: RegimenFiscal,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regimen-fiscal/new',
      name: 'RegimenFiscalCreate',
      component: RegimenFiscalUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regimen-fiscal/:regimenFiscalId/edit',
      name: 'RegimenFiscalEdit',
      component: RegimenFiscalUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regimen-fiscal/:regimenFiscalId/view',
      name: 'RegimenFiscalView',
      component: RegimenFiscalDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estados',
      name: 'Estados',
      component: Estados,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estados/new',
      name: 'EstadosCreate',
      component: EstadosUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estados/:estadosId/edit',
      name: 'EstadosEdit',
      component: EstadosUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estados/:estadosId/view',
      name: 'EstadosView',
      component: EstadosDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estatus-cfdi',
      name: 'EstatusCfdi',
      component: EstatusCfdi,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estatus-cfdi/new',
      name: 'EstatusCfdiCreate',
      component: EstatusCfdiUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estatus-cfdi/:estatusCfdiId/edit',
      name: 'EstatusCfdiEdit',
      component: EstatusCfdiUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estatus-cfdi/:estatusCfdiId/view',
      name: 'EstatusCfdiView',
      component: EstatusCfdiDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'motivos-cancelacion',
      name: 'MotivosCancelacion',
      component: MotivosCancelacion,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'motivos-cancelacion/new',
      name: 'MotivosCancelacionCreate',
      component: MotivosCancelacionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'motivos-cancelacion/:motivosCancelacionId/edit',
      name: 'MotivosCancelacionEdit',
      component: MotivosCancelacionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'motivos-cancelacion/:motivosCancelacionId/view',
      name: 'MotivosCancelacionView',
      component: MotivosCancelacionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-cfdi',
      name: 'TipoCfdi',
      component: TipoCfdi,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-cfdi/new',
      name: 'TipoCfdiCreate',
      component: TipoCfdiUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-cfdi/:tipoCfdiId/edit',
      name: 'TipoCfdiEdit',
      component: TipoCfdiUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-cfdi/:tipoCfdiId/view',
      name: 'TipoCfdiView',
      component: TipoCfdiDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-recibo',
      name: 'TipoRecibo',
      component: TipoRecibo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-recibo/new',
      name: 'TipoReciboCreate',
      component: TipoReciboUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-recibo/:tipoReciboId/edit',
      name: 'TipoReciboEdit',
      component: TipoReciboUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-recibo/:tipoReciboId/view',
      name: 'TipoReciboView',
      component: TipoReciboDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'representacion-grafica',
      name: 'RepresentacionGrafica',
      component: RepresentacionGrafica,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'representacion-grafica/new',
      name: 'RepresentacionGraficaCreate',
      component: RepresentacionGraficaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'representacion-grafica/:representacionGraficaId/edit',
      name: 'RepresentacionGraficaEdit',
      component: RepresentacionGraficaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'representacion-grafica/:representacionGraficaId/view',
      name: 'RepresentacionGraficaView',
      component: RepresentacionGraficaDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
