import { Component, Vue, Inject } from 'vue-property-decorator';

import { maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IRegimenFiscal, RegimenFiscal } from '@/shared/model/regimen-fiscal.model';
import RegimenFiscalService from './regimen-fiscal.service';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const validations: any = {
  regimenFiscal: {
    fecha: {},
    regimenFiscal: {
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
export default class RegimenFiscalUpdate extends Vue {
  @Inject('regimenFiscalService') private regimenFiscalService: () => RegimenFiscalService;
  @Inject('alertService') private alertService: () => AlertService;

  public regimenFiscal: IRegimenFiscal = new RegimenFiscal();
  public estatusValues: string[] = Object.keys(Estatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.regimenFiscalId) {
        vm.retrieveRegimenFiscal(to.params.regimenFiscalId);
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
    if (this.regimenFiscal.id) {
      this.regimenFiscalService()
        .update(this.regimenFiscal)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('timbradoCatalogosApp.regimenFiscal.updated', { param: param.id });
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
      this.regimenFiscalService()
        .create(this.regimenFiscal)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('timbradoCatalogosApp.regimenFiscal.created', { param: param.id });
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

  public retrieveRegimenFiscal(regimenFiscalId): void {
    this.regimenFiscalService()
      .find(regimenFiscalId)
      .then(res => {
        this.regimenFiscal = res;
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
