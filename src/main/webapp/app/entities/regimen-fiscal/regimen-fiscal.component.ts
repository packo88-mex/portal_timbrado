import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRegimenFiscal } from '@/shared/model/regimen-fiscal.model';

import RegimenFiscalService from './regimen-fiscal.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RegimenFiscal extends Vue {
  @Inject('regimenFiscalService') private regimenFiscalService: () => RegimenFiscalService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public regimenFiscals: IRegimenFiscal[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRegimenFiscals();
  }

  public clear(): void {
    this.retrieveAllRegimenFiscals();
  }

  public retrieveAllRegimenFiscals(): void {
    this.isFetching = true;
    this.regimenFiscalService()
      .retrieve()
      .then(
        res => {
          this.regimenFiscals = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IRegimenFiscal): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRegimenFiscal(): void {
    this.regimenFiscalService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('timbradoCatalogosApp.regimenFiscal.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllRegimenFiscals();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
