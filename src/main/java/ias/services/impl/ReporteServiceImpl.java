package ias.services.impl;

import ias.genericos.GenericServiceImpl;
import ias.entity.Reporte;
import ias.repositories.ReporteDAO;
import ias.services.api.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteServiceImpl extends GenericServiceImpl<Reporte, Long> implements ReporteService {
    @Autowired
    private ReporteDAO dao;

    @Override
    public JpaRepository<Reporte, Long> getDao() {
        return dao;
    }

    @Override
    public List<Reporte> findAllByTecnico(String tecnico) {
        return dao.findAllByTecnico(tecnico);
    }
}
