import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEstados } from '@/shared/model/estados.model';

import EstadosService from './estados.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Estados extends Vue {
  @Inject('estadosService') private estadosService: () => EstadosService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public estados: IEstados[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllEstadoss();
  }

  public clear(): void {
    this.retrieveAllEstadoss();
  }

  public retrieveAllEstadoss(): void {
    this.isFetching = true;
    this.estadosService()
      .retrieve()
      .then(
        res => {
          this.estados = res.data;
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

  public prepareRemove(instance: IEstados): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeEstados(): void {
    this.estadosService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('timbradoCatalogosApp.estados.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllEstadoss();
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
