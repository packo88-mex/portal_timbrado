package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegimenFiscalMapperTest {

    private RegimenFiscalMapper regimenFiscalMapper;

    @BeforeEach
    public void setUp() {
        regimenFiscalMapper = new RegimenFiscalMapperImpl();
    }
}
