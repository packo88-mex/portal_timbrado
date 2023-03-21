import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRegimenFiscal } from '@/shared/model/regimen-fiscal.model';
import RegimenFiscalService from './regimen-fiscal.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RegimenFiscalDetails extends Vue {
  @Inject('regimenFiscalService') private regimenFiscalService: () => RegimenFiscalService;
  @Inject('alertService') private alertService: () => AlertService;

  public regimenFiscal: IRegimenFiscal = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.regimenFiscalId) {
        vm.retrieveRegimenFiscal(to.params.regimenFiscalId);
      }
    });
  }

  public retrieveRegimenFiscal(regimenFiscalId) {
    this.regimenFiscalService()
      .find(regimenFiscalId)
      .then(res => {
        this.regimenFiscal = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
