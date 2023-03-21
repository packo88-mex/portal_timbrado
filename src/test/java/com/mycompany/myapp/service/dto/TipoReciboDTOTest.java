package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoReciboDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoReciboDTO.class);
        TipoReciboDTO tipoReciboDTO1 = new TipoReciboDTO();
        tipoReciboDTO1.setId(1L);
        TipoReciboDTO tipoReciboDTO2 = new TipoReciboDTO();
        assertThat(tipoReciboDTO1).isNotEqualTo(tipoReciboDTO2);
        tipoReciboDTO2.setId(tipoReciboDTO1.getId());
        assertThat(tipoReciboDTO1).isEqualTo(tipoReciboDTO2);
        tipoReciboDTO2.setId(2L);
        assertThat(tipoReciboDTO1).isNotEqualTo(tipoReciboDTO2);
        tipoReciboDTO1.setId(null);
        assertThat(tipoReciboDTO1).isNotEqualTo(tipoReciboDTO2);
    }
}
