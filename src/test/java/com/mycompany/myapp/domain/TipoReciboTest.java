package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoReciboTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoRecibo.class);
        TipoRecibo tipoRecibo1 = new TipoRecibo();
        tipoRecibo1.setId(1L);
        TipoRecibo tipoRecibo2 = new TipoRecibo();
        tipoRecibo2.setId(tipoRecibo1.getId());
        assertThat(tipoRecibo1).isEqualTo(tipoRecibo2);
        tipoRecibo2.setId(2L);
        assertThat(tipoRecibo1).isNotEqualTo(tipoRecibo2);
        tipoRecibo1.setId(null);
        assertThat(tipoRecibo1).isNotEqualTo(tipoRecibo2);
    }
}
