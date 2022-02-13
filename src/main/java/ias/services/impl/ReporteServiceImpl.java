package ias.services.impl;

import ias.genericos.GenericServiceImpl;
import ias.modelos.Reporte;
import ias.repositories.ReporteDAO;
import ias.services.api.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ReporteServiceImpl extends GenericServiceImpl<Reporte, Long> implements ReporteService {
    @Autowired
    private ReporteDAO dao;

    @Override
    public JpaRepository<Reporte, Long> getDao() {
        return dao;
    }
}
