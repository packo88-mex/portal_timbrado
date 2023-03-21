package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RepresentacionGraficaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepresentacionGrafica.class);
        RepresentacionGrafica representacionGrafica1 = new RepresentacionGrafica();
        representacionGrafica1.setId(1L);
        RepresentacionGrafica representacionGrafica2 = new RepresentacionGrafica();
        representacionGrafica2.setId(representacionGrafica1.getId());
        assertThat(representacionGrafica1).isEqualTo(representacionGrafica2);
        representacionGrafica2.setId(2L);
        assertThat(representacionGrafica1).isNotEqualTo(representacionGrafica2);
        representacionGrafica1.setId(null);
        assertThat(representacionGrafica1).isNotEqualTo(representacionGrafica2);
    }
}
