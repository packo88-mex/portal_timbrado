package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoCfdiDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoCfdiDTO.class);
        TipoCfdiDTO tipoCfdiDTO1 = new TipoCfdiDTO();
        tipoCfdiDTO1.setId(1L);
        TipoCfdiDTO tipoCfdiDTO2 = new TipoCfdiDTO();
        assertThat(tipoCfdiDTO1).isNotEqualTo(tipoCfdiDTO2);
        tipoCfdiDTO2.setId(tipoCfdiDTO1.getId());
        assertThat(tipoCfdiDTO1).isEqualTo(tipoCfdiDTO2);
        tipoCfdiDTO2.setId(2L);
        assertThat(tipoCfdiDTO1).isNotEqualTo(tipoCfdiDTO2);
        tipoCfdiDTO1.setId(null);
        assertThat(tipoCfdiDTO1).isNotEqualTo(tipoCfdiDTO2);
    }
}
