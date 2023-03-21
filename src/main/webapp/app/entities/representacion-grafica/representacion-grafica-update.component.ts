import { Component, Vue, Inject } from 'vue-property-decorator';

import { maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import TipoReciboService from '@/entities/tipo-recibo/tipo-recibo.service';
import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';

import { IRepresentacionGrafica, RepresentacionGrafica } from '@/shared/model/representacion-grafica.model';
import RepresentacionGraficaService from './representacion-grafica.service';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const validations: any = {
  representacionGrafica: {
    fecha: {},
    representacionGrafica: {
      maxLength: maxLength(100),
    },
    estatus: {},
    fechaModificacion: {},
    usuario: {
      maxLength: maxLength(30),
    },
  },
};

@Component({
  validations,
})
export default class RepresentacionGraficaUpdate extends Vue {
  @Inject('representacionGraficaService') private representacionGraficaService: () => RepresentacionGraficaService;
  @Inject('alertService') private alertService: () => AlertService;

  public representacionGrafica: IRepresentacionGrafica = new RepresentacionGrafica();

  @Inject('tipoReciboService') private tipoReciboService: () => TipoReciboService;

  public tipoRecibos: ITipoRecibo[] = [];
  public estatusValues: string[] = Object.keys(Estatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.representacionGraficaId) {
        vm.retrieveRepresentacionGrafica(to.params.representacionGraficaId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.representacionGrafica.id) {
      this.representacionGraficaService()
        .update(this.representacionGrafica)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('timbradoCatalogosApp.representacionGrafica.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.representacionGraficaService()
        .create(this.representacionGrafica)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('timbradoCatalogosApp.representacionGrafica.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveRepresentacionGrafica(representacionGraficaId): void {
    this.representacionGraficaService()
      .find(representacionGraficaId)
      .then(res => {
        this.representacionGrafica = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.tipoReciboService()
      .retrieve()
      .then(res => {
        this.tipoRecibos = res.data;
      });
  }
}
