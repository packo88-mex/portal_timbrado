import { Component, Vue, Inject } from 'vue-property-decorator';

import { maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import TipoReciboService from '@/entities/tipo-recibo/tipo-recibo.service';
import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';

import { ITipoCfdi, TipoCfdi } from '@/shared/model/tipo-cfdi.model';
import TipoCfdiService from './tipo-cfdi.service';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const validations: any = {
  tipoCfdi: {
    fecha: {},
    tipoCfdi: {
      maxLength: maxLength(50),
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
export default class TipoCfdiUpdate extends Vue {
  @Inject('tipoCfdiService') private tipoCfdiService: () => TipoCfdiService;
  @Inject('alertService') private alertService: () => AlertService;

  public tipoCfdi: ITipoCfdi = new TipoCfdi();

  @Inject('tipoReciboService') private tipoReciboService: () => TipoReciboService;

  public tipoRecibos: ITipoRecibo[] = [];
  public estatusValues: string[] = Object.keys(Estatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tipoCfdiId) {
        vm.retrieveTipoCfdi(to.params.tipoCfdiId);
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
    if (this.tipoCfdi.id) {
      this.tipoCfdiService()
        .update(this.tipoCfdi)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('timbradoCatalogosApp.tipoCfdi.updated', { param: param.id });
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
      this.tipoCfdiService()
        .create(this.tipoCfdi)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('timbradoCatalogosApp.tipoCfdi.created', { param: param.id });
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

  public retrieveTipoCfdi(tipoCfdiId): void {
    this.tipoCfdiService()
      .find(tipoCfdiId)
      .then(res => {
        this.tipoCfdi = res;
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
