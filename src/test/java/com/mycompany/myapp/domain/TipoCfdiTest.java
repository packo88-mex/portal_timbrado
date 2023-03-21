package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoCfdiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoCfdi.class);
        TipoCfdi tipoCfdi1 = new TipoCfdi();
        tipoCfdi1.setId(1L);
        TipoCfdi tipoCfdi2 = new TipoCfdi();
        tipoCfdi2.setId(tipoCfdi1.getId());
        assertThat(tipoCfdi1).isEqualTo(tipoCfdi2);
        tipoCfdi2.setId(2L);
        assertThat(tipoCfdi1).isNotEqualTo(tipoCfdi2);
        tipoCfdi1.setId(null);
        assertThat(tipoCfdi1).isNotEqualTo(tipoCfdi2);
    }
}
