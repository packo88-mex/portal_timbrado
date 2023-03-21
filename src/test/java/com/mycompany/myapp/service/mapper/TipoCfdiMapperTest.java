package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TipoCfdiMapperTest {

    private TipoCfdiMapper tipoCfdiMapper;

    @BeforeEach
    public void setUp() {
        tipoCfdiMapper = new TipoCfdiMapperImpl();
    }
}
