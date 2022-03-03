package ias.services.impl;

import ias.models.Reporte;
import ias.repositories.ReporteDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static ias.data.DataService.REPORTES_0;
import static ias.data.DataService.REPORTES_1;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReporteServiceImplTest {

    ReporteDAO dao;

    ReporteServiceImpl service;

    @BeforeEach
    void setUp() {
        dao = mock(ReporteDAO.class);
        service = new ReporteServiceImpl(dao);
    }

    @Test
    void testFindAll() {


        when(dao.findAll()).thenReturn(REPORTES_1);

        List<Reporte> reportes = service.getAll();
        assertNotNull(reportes);
        assertEquals(8, reportes.size());
    }

    @Test
    void testFindAllListaVacia() {

        when(dao.findAll()).thenReturn(REPORTES_0);

        List<Reporte> reportes = service.getAll();
        assertNotNull(reportes);
        assertTrue(reportes.isEmpty());
    }
}