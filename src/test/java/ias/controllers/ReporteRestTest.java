package ias.controllers;

import ias.enums.ListaServicios;
import ias.models.Reporte;
import ias.services.api.ReporteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;

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

    @MockBean
    private CalculadoraRest rest;

    @Test
    void testFindAll() throws Exception {
        when(service.findAll()).thenReturn(RESPONSE_REPORTES());

        mockMvc.perform(get("/reportes/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(service).findAll();

        List<Reporte> reportes = (List<Reporte>) service.findAll().getBody().get("reportes");
        assertEquals(reportes, RESPONSE_REPORTES().getBody().get("reportes"));
    }

    @Test
    void testFindAllServicios() throws Exception {
        when(service.findAllServicios()).thenReturn(RESPONSE_SERVICIOS());
        mockMvc.perform(get("/reportes/servicios/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(service).findAllServicios();

        List<ListaServicios> servicios = (List<ListaServicios>) service.findAllServicios().getBody().get("servicios");
        assertEquals(servicios, RESPONSE_SERVICIOS().getBody().get("servicios"));
    }

    @Test
    void testFindAllByTecnico() throws Exception {
        when(rest.findAllByTecnico("1082749257", 2022, 9)).thenReturn(RESPONSE_CALCULO_HORAS());

        mockMvc.perform(get("/calculos/1082749257/2022/9/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        ResponseEntity<HashMap<String, Object>> response = rest.findAllByTecnico("1082749257", 2022, 9);
        assertEquals(response.getBody().get("mensaje"), RESPONSE_CALCULO_HORAS().getBody().get("mensaje"));
    }
}