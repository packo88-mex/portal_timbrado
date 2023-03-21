package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RepresentacionGraficaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepresentacionGraficaDTO.class);
        RepresentacionGraficaDTO representacionGraficaDTO1 = new RepresentacionGraficaDTO();
        representacionGraficaDTO1.setId(1L);
        RepresentacionGraficaDTO representacionGraficaDTO2 = new RepresentacionGraficaDTO();
        assertThat(representacionGraficaDTO1).isNotEqualTo(representacionGraficaDTO2);
        representacionGraficaDTO2.setId(representacionGraficaDTO1.getId());
        assertThat(representacionGraficaDTO1).isEqualTo(representacionGraficaDTO2);
        representacionGraficaDTO2.setId(2L);
        assertThat(representacionGraficaDTO1).isNotEqualTo(representacionGraficaDTO2);
        representacionGraficaDTO1.setId(null);
        assertThat(representacionGraficaDTO1).isNotEqualTo(representacionGraficaDTO2);
    }
}
