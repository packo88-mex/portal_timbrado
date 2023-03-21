package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegimenFiscalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegimenFiscal.class);
        RegimenFiscal regimenFiscal1 = new RegimenFiscal();
        regimenFiscal1.setId(1L);
        RegimenFiscal regimenFiscal2 = new RegimenFiscal();
        regimenFiscal2.setId(regimenFiscal1.getId());
        assertThat(regimenFiscal1).isEqualTo(regimenFiscal2);
        regimenFiscal2.setId(2L);
        assertThat(regimenFiscal1).isNotEqualTo(regimenFiscal2);
        regimenFiscal1.setId(null);
        assertThat(regimenFiscal1).isNotEqualTo(regimenFiscal2);
    }
}
