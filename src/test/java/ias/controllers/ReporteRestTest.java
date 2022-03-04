package ias.controllers;

import ias.enums.ListaServicios;
import ias.models.Reporte;
import ias.services.api.ReporteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.GregorianCalendar;

import static ias.data.DataService.*;

import static ias.data.DataService.RESPONSE_REPORTES;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReporteRest.class)
class ReporteRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService service;

    @Test
    void testFindAll() throws Exception {
        when(service.findAll()).thenReturn(RESPONSE_REPORTES());

        mockMvc.perform(get("/reportes/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(service).findAll();
    }
}