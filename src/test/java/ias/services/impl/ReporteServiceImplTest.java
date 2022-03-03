package ias.services.impl;

import ias.enums.ListaServicios;
import ias.models.Reporte;
import ias.repositories.ReporteDAO;
import ias.utilities.Calculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static ias.data.DataService.*;
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

    @Nested
    class CrudOperations {
        @Test
        void testGetAll() {
            when(dao.findAll()).thenReturn(REPORTES_1);

            List<Reporte> reportes = service.getAll();
            assertNotNull(reportes);
            assertEquals(8, reportes.size());
        }

        @Test
        void testGetAllListaVacia() {
            when(dao.findAll()).thenReturn(REPORTES_0);

            List<Reporte> reportes = service.getAll();
            assertNotNull(reportes);
            assertTrue(reportes.isEmpty());
        }

        @Test
        void testSave() {
            final Reporte reporte = new Reporte(1, "1082749257", ListaServicios.A,
                    new Date(), new Date());
            when(dao.save(REPORTE_0)).thenReturn(reporte);
            Reporte guardado = service.save(REPORTE_0);

            assertNotNull(guardado);
            assertNotNull(reporte);
            assertEquals(guardado, reporte);
        }
    }

    @Nested
    class CrudResponses {
        @Test
        void testFindAll() {
            when(dao.findAll()).thenReturn(REPORTES_2);

            List<Reporte> reportes = service.getAll();
            assertNotNull(reportes);
            assertEquals(12, reportes.size());

            ResponseEntity<HashMap<String, Object>> response = service.findAll();
            assertNotNull(response);

            List<Reporte> respuesta = (List<Reporte>) (response.getBody().get("reportes"));
            assertNotNull(respuesta);
            assertEquals(respuesta, reportes);
        }

        @Test
        void testFindAllListaVacia() {
            when(dao.findAll()).thenReturn(REPORTES_0);

            List<Reporte> reportes = service.getAll();
            assertNotNull(reportes);
            assertTrue(reportes.isEmpty());

            ResponseEntity<HashMap<String, Object>> response = service.findAll();
            assertNotNull(response);

            List<Reporte> respuesta = (List<Reporte>) (response.getBody().get("reportes"));
            assertTrue(respuesta.isEmpty());
            assertEquals(Collections.emptyList(), respuesta);
        }

        @Test
        void testCreate() {
            final Reporte reporte = new Reporte(1, "1082749257", ListaServicios.A,
                    new Date(), new Date());
            when(dao.save(REPORTE_1)).thenReturn(reporte);
            Reporte guardado = service.save(REPORTE_1);

            assertNotNull(guardado);
            assertNotNull(reporte);
            assertEquals(guardado, reporte);

            ResponseEntity<HashMap<String, Object>> response = service.create(reporte, null);
            assertEquals(400, response.getStatusCodeValue());


            reporte.getFecha_finalizacion().setDate(reporte.getFecha_finalizacion().getDate() + 1);
            response = service.create(reporte, null);
            assertEquals(201, response.getStatusCodeValue());
        }
    }

    @Nested
    class CalculadoraTest {
        @Test
        void testFindAllByTecnico() {
            when(dao.findAll()).thenReturn(REPORTES_2);

            List<Reporte> reportes = service.getAll();
            assertNotNull(reportes);
            assertEquals(12, reportes.size());

            List<Reporte> r1082749257 = reportes.stream()
                    .filter(reporte -> reporte.getTecnico() == "1082749257").collect(Collectors.toList());
            List<Reporte> r87570236 = reportes.stream()
                    .filter(reporte -> reporte.getTecnico() == "87570236").collect(Collectors.toList());
            List<Reporte> r27156864 = reportes.stream()
                    .filter(reporte -> reporte.getTecnico() == "27156864").collect(Collectors.toList());

            assertEquals(4, r1082749257.size());
            assertEquals(4, r87570236.size());
            assertEquals(4, r27156864.size());

            r1082749257.stream().forEach(reporte -> {
                Calculo calculo = new Calculo();
                calculo.setSemana(5);
                calculo.setInicio(reporte.getFecha_inicio());
                calculo.setFin(reporte.getFecha_finalizacion());
                calculo.calcularHoras();
                assertEquals(13,calculo.getNormales());
                assertEquals(11,calculo.getNocturnas());
            });

            r87570236.stream().forEach(reporte -> {
                Calculo calculo = new Calculo();
                calculo.setSemana(5);
                calculo.setInicio(reporte.getFecha_inicio());
                calculo.setFin(reporte.getFecha_finalizacion());
                calculo.calcularHoras();
                assertEquals(13,calculo.getNormales());
                assertEquals(11,calculo.getNocturnas());
            });

            r27156864.stream().forEach(reporte -> {
                Calculo calculo = new Calculo();
                calculo.setSemana(5);
                calculo.setInicio(reporte.getFecha_inicio());
                calculo.setFin(reporte.getFecha_finalizacion());
                calculo.calcularHoras();
                assertEquals(13,calculo.getNormales());
                assertEquals(11,calculo.getNocturnas());
            });

        }
    }
}