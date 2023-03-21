import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMotivosCancelacion } from '@/shared/model/motivos-cancelacion.model';

import MotivosCancelacionService from './motivos-cancelacion.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class MotivosCancelacion extends Vue {
  @Inject('motivosCancelacionService') private motivosCancelacionService: () => MotivosCancelacionService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public motivosCancelacions: IMotivosCancelacion[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllMotivosCancelacions();
  }

  public clear(): void {
    this.retrieveAllMotivosCancelacions();
  }

  public retrieveAllMotivosCancelacions(): void {
    this.isFetching = true;
    this.motivosCancelacionService()
      .retrieve()
      .then(
        res => {
          this.motivosCancelacions = res.data;
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

  public prepareRemove(instance: IMotivosCancelacion): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeMotivosCancelacion(): void {
    this.motivosCancelacionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('timbradoCatalogosApp.motivosCancelacion.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllMotivosCancelacions();
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
