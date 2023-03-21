import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEstatusCfdi } from '@/shared/model/estatus-cfdi.model';
import EstatusCfdiService from './estatus-cfdi.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class EstatusCfdiDetails extends Vue {
  @Inject('estatusCfdiService') private estatusCfdiService: () => EstatusCfdiService;
  @Inject('alertService') private alertService: () => AlertService;

  public estatusCfdi: IEstatusCfdi = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.estatusCfdiId) {
        vm.retrieveEstatusCfdi(to.params.estatusCfdiId);
      }
    });
  }

  public retrieveEstatusCfdi(estatusCfdiId) {
    this.estatusCfdiService()
      .find(estatusCfdiId)
      .then(res => {
        this.estatusCfdi = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
