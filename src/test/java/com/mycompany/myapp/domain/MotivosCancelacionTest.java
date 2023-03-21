package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MotivosCancelacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MotivosCancelacion.class);
        MotivosCancelacion motivosCancelacion1 = new MotivosCancelacion();
        motivosCancelacion1.setId(1L);
        MotivosCancelacion motivosCancelacion2 = new MotivosCancelacion();
        motivosCancelacion2.setId(motivosCancelacion1.getId());
        assertThat(motivosCancelacion1).isEqualTo(motivosCancelacion2);
        motivosCancelacion2.setId(2L);
        assertThat(motivosCancelacion1).isNotEqualTo(motivosCancelacion2);
        motivosCancelacion1.setId(null);
        assertThat(motivosCancelacion1).isNotEqualTo(motivosCancelacion2);
    }
}
