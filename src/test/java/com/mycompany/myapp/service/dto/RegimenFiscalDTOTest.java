package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegimenFiscalDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegimenFiscalDTO.class);
        RegimenFiscalDTO regimenFiscalDTO1 = new RegimenFiscalDTO();
        regimenFiscalDTO1.setId(1L);
        RegimenFiscalDTO regimenFiscalDTO2 = new RegimenFiscalDTO();
        assertThat(regimenFiscalDTO1).isNotEqualTo(regimenFiscalDTO2);
        regimenFiscalDTO2.setId(regimenFiscalDTO1.getId());
        assertThat(regimenFiscalDTO1).isEqualTo(regimenFiscalDTO2);
        regimenFiscalDTO2.setId(2L);
        assertThat(regimenFiscalDTO1).isNotEqualTo(regimenFiscalDTO2);
        regimenFiscalDTO1.setId(null);
        assertThat(regimenFiscalDTO1).isNotEqualTo(regimenFiscalDTO2);
    }
}
