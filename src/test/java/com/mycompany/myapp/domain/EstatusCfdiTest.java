package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstatusCfdiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusCfdi.class);
        EstatusCfdi estatusCfdi1 = new EstatusCfdi();
        estatusCfdi1.setId(1L);
        EstatusCfdi estatusCfdi2 = new EstatusCfdi();
        estatusCfdi2.setId(estatusCfdi1.getId());
        assertThat(estatusCfdi1).isEqualTo(estatusCfdi2);
        estatusCfdi2.setId(2L);
        assertThat(estatusCfdi1).isNotEqualTo(estatusCfdi2);
        estatusCfdi1.setId(null);
        assertThat(estatusCfdi1).isNotEqualTo(estatusCfdi2);
    }
}
