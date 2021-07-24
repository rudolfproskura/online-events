package online.events.dao;


import online.events.dto.VelicinaGradaDto;
import online.events.model.VelicinaGrada;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class VelicinaGradaDao extends GenericDao<VelicinaGrada, VelicinaGradaDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    protected VelicinaGrada formEntity(VelicinaGradaDto dto) {
        return null;//TODO
    }

    @Override
    protected VelicinaGradaDto formDTO(VelicinaGrada entity) {
        VelicinaGradaDto velicinaGradaDto = null;
        if (entity != null) {
            velicinaGradaDto = new VelicinaGradaDto();
            VelicinaGrada velicinaGradaEntity = (VelicinaGrada) entity;
            velicinaGradaDto.setSifraVelicineGrada(velicinaGradaEntity.getSifraVelicineGrada());
            velicinaGradaDto.setNazivVelicineGrada(entity.getNazivVelicineGrada());
            velicinaGradaDto.setAktivan(velicinaGradaEntity.getAktivanVelicinaGrada());
        }
        return velicinaGradaDto;
    }

    @Override
    protected String getBasicSql() {
        return "SELECT e FROM VelicinaGrada e";
    }

}
