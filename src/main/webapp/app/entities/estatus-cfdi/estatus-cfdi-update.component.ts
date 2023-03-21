import { Component, Vue, Inject } from 'vue-property-decorator';

import { maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IEstatusCfdi, EstatusCfdi } from '@/shared/model/estatus-cfdi.model';
import EstatusCfdiService from './estatus-cfdi.service';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const validations: any = {
  estatusCfdi: {
    fecha: {},
    descripcionEstatusCfdi: {
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
export default class EstatusCfdiUpdate extends Vue {
  @Inject('estatusCfdiService') private estatusCfdiService: () => EstatusCfdiService;
  @Inject('alertService') private alertService: () => AlertService;

  public estatusCfdi: IEstatusCfdi = new EstatusCfdi();
  public estatusValues: string[] = Object.keys(Estatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.estatusCfdiId) {
        vm.retrieveEstatusCfdi(to.params.estatusCfdiId);
      }
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
    if (this.estatusCfdi.id) {
      this.estatusCfdiService()
        .update(this.estatusCfdi)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('timbradoCatalogosApp.estatusCfdi.updated', { param: param.id });
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
      this.estatusCfdiService()
        .create(this.estatusCfdi)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('timbradoCatalogosApp.estatusCfdi.created', { param: param.id });
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

  public retrieveEstatusCfdi(estatusCfdiId): void {
    this.estatusCfdiService()
      .find(estatusCfdiId)
      .then(res => {
        this.estatusCfdi = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
