package ias.services.impl;

import ias.enums.ListaServicios;
import ias.models.Reporte;
import ias.repositories.ReporteDAO;
import ias.services.api.ReporteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReporteServiceImplTest {

    @Test
    void testFindAll() {
        ReporteDAO dao = mock(ReporteDAO.class);
        ReporteServiceImpl service = new ReporteServiceImpl(dao);

        List<Reporte> REPORTES_A = Arrays.asList(
                new Reporte(0, "1082749257", ListaServicios.A, new Date(), new Date()),
                new Reporte(0, "1082749257", ListaServicios.A, new Date(), new Date()),
                new Reporte(0, "1082749257", ListaServicios.B, new Date(), new Date()),
                new Reporte(0, "1082749257", ListaServicios.C, new Date(), new Date()),
                new Reporte(0, "87570236", ListaServicios.A, new Date(), new Date()),
                new Reporte(0, "87570236", ListaServicios.D, new Date(), new Date()),
                new Reporte(0, "27156864", ListaServicios.C, new Date(), new Date()),
                new Reporte(0, "27156864", ListaServicios.B, new Date(), new Date()));

        when(dao.findAll()).thenReturn(REPORTES_A);

        List<Reporte> reportes = service.getAll();
        assertNotNull(reportes);
        assertEquals(8, reportes.size());
    }

    @Test
    void testFindAllListaVacia() {
        ReporteDAO dao = mock(ReporteDAO.class);
        ReporteServiceImpl service = new ReporteServiceImpl(dao);

        List<Reporte> REPORTES_A = Collections.emptyList();

        when(dao.findAll()).thenReturn(REPORTES_A);

        List<Reporte> reportes = service.getAll();
        assertNotNull(reportes);
        assertTrue(reportes.isEmpty());
        assertEquals(0, reportes.size());
    }
}