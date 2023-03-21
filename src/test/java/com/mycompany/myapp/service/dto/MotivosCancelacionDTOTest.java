package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MotivosCancelacionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivosCancelacionDTO.class);
        MotivosCancelacionDTO motivosCancelacionDTO1 = new MotivosCancelacionDTO();
        motivosCancelacionDTO1.setId(1L);
        MotivosCancelacionDTO motivosCancelacionDTO2 = new MotivosCancelacionDTO();
        assertThat(motivosCancelacionDTO1).isNotEqualTo(motivosCancelacionDTO2);
        motivosCancelacionDTO2.setId(motivosCancelacionDTO1.getId());
        assertThat(motivosCancelacionDTO1).isEqualTo(motivosCancelacionDTO2);
        motivosCancelacionDTO2.setId(2L);
        assertThat(motivosCancelacionDTO1).isNotEqualTo(motivosCancelacionDTO2);
        motivosCancelacionDTO1.setId(null);
        assertThat(motivosCancelacionDTO1).isNotEqualTo(motivosCancelacionDTO2);
    }
}
