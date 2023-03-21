import { Component, Vue, Inject } from 'vue-property-decorator';

import { maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IMotivosCancelacion, MotivosCancelacion } from '@/shared/model/motivos-cancelacion.model';
import MotivosCancelacionService from './motivos-cancelacion.service';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const validations: any = {
  motivosCancelacion: {
    fecha: {},
    motivoCancelacion: {
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
export default class MotivosCancelacionUpdate extends Vue {
  @Inject('motivosCancelacionService') private motivosCancelacionService: () => MotivosCancelacionService;
  @Inject('alertService') private alertService: () => AlertService;

  public motivosCancelacion: IMotivosCancelacion = new MotivosCancelacion();
  public estatusValues: string[] = Object.keys(Estatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.motivosCancelacionId) {
        vm.retrieveMotivosCancelacion(to.params.motivosCancelacionId);
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
    if (this.motivosCancelacion.id) {
      this.motivosCancelacionService()
        .update(this.motivosCancelacion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('timbradoCatalogosApp.motivosCancelacion.updated', { param: param.id });
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
      this.motivosCancelacionService()
        .create(this.motivosCancelacion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('timbradoCatalogosApp.motivosCancelacion.created', { param: param.id });
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

  public retrieveMotivosCancelacion(motivosCancelacionId): void {
    this.motivosCancelacionService()
      .find(motivosCancelacionId)
      .then(res => {
        this.motivosCancelacion = res;
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
