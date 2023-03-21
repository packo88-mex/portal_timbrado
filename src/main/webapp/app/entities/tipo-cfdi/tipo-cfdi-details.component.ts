import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITipoCfdi } from '@/shared/model/tipo-cfdi.model';
import TipoCfdiService from './tipo-cfdi.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TipoCfdiDetails extends Vue {
  @Inject('tipoCfdiService') private tipoCfdiService: () => TipoCfdiService;
  @Inject('alertService') private alertService: () => AlertService;

  public tipoCfdi: ITipoCfdi = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tipoCfdiId) {
        vm.retrieveTipoCfdi(to.params.tipoCfdiId);
      }
    });
  }

  public retrieveTipoCfdi(tipoCfdiId) {
    this.tipoCfdiService()
      .find(tipoCfdiId)
      .then(res => {
        this.tipoCfdi = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
