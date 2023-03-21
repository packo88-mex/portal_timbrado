package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstatusCfdiDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusCfdiDTO.class);
        EstatusCfdiDTO estatusCfdiDTO1 = new EstatusCfdiDTO();
        estatusCfdiDTO1.setId(1L);
        EstatusCfdiDTO estatusCfdiDTO2 = new EstatusCfdiDTO();
        assertThat(estatusCfdiDTO1).isNotEqualTo(estatusCfdiDTO2);
        estatusCfdiDTO2.setId(estatusCfdiDTO1.getId());
        assertThat(estatusCfdiDTO1).isEqualTo(estatusCfdiDTO2);
        estatusCfdiDTO2.setId(2L);
        assertThat(estatusCfdiDTO1).isNotEqualTo(estatusCfdiDTO2);
        estatusCfdiDTO1.setId(null);
        assertThat(estatusCfdiDTO1).isNotEqualTo(estatusCfdiDTO2);
    }
}
