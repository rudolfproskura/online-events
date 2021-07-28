package online.events.dao;



import online.events.dto.GradDto;
import online.events.dto.OrganizacijskaJedinicaDto;
import online.events.dto.VelicinaGradaDto;
import online.events.model.Grad;
import online.events.model.OrganizacijskaJedinica;
import online.events.model.VelicinaGrada;
import online.events.util.DogadajAppUtil;

import javax.faces.model.SelectItem;
import javax.persistence.Query;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class GradDao extends GenericDao<Grad, GradDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    protected Grad formEntity(GradDto dto) {
        Grad entity = null;
        if (dto != null) {
            entity = new Grad();
            entity.setSifraGrada(dto.getSifraGrada());
            entity.setNazivGrada(dto.getNazivGrada());
            if (dto.getVelicinaGradaDto().getSifraVelicineGrada() != null) {
                entity.setVelicinaGrada(getEntityManager().getReference(VelicinaGrada.class, dto.getVelicinaGradaDto().getSifraVelicineGrada()));
            }
            if (dto.getOrganizacijskaJedinicaDto().getSifraOrgJedinice() != null) {
                entity.setOrganizacijskaJedinica(getEntityManager().getReference(OrganizacijskaJedinica.class, dto.getOrganizacijskaJedinicaDto().getSifraOrgJedinice()));
            }
        }
        return entity;
    }

    @Override
    protected GradDto formDTO(Grad entity) {
        GradDto gradDto = null;
        if (entity != null) {
            gradDto = new GradDto();
            Grad gradEntity = (Grad) entity;
            gradDto.setSifraGrada(entity.getSifraGrada());
            gradDto.setNazivGrada(entity.getNazivGrada());
            OrganizacijskaJedinicaDto organizacijskaJedinicaDto = new OrganizacijskaJedinicaDto();
            organizacijskaJedinicaDto.setSifraOrgJedinice(entity.getOrganizacijskaJedinica().getSifraOrgJedinice());
            organizacijskaJedinicaDto.setNazivOrgJedinice(entity.getOrganizacijskaJedinica().getNazivOrgJedinice());
            organizacijskaJedinicaDto.setOpisOrgJedinice(entity.getOrganizacijskaJedinica().getOpisOrgJedinice());
            if (entity.getOrganizacijskaJedinica().getNadredenaOrgJedinica() != null) {
                OrganizacijskaJedinicaDto nadredenaOrganizacijskaJedinicaDto = new OrganizacijskaJedinicaDto();
                nadredenaOrganizacijskaJedinicaDto.setSifraOrgJedinice(entity.getOrganizacijskaJedinica().getNadredenaOrgJedinica().getSifraOrgJedinice());
                nadredenaOrganizacijskaJedinicaDto.setNazivOrgJedinice(entity.getOrganizacijskaJedinica().getNadredenaOrgJedinica().getNazivOrgJedinice());
                organizacijskaJedinicaDto.setNadredenaOrganizacijeDto(nadredenaOrganizacijskaJedinicaDto);
            }
            if (entity.getVelicinaGrada() != null) {
                VelicinaGradaDto velicinaGradaDto = new VelicinaGradaDto();
                velicinaGradaDto.setSifraVelicineGrada(entity.getVelicinaGrada().getSifraVelicineGrada());
                gradDto.setVelicinaGradaDto(velicinaGradaDto);
            }
            gradDto.setOrganizacijskaJedinicaDto(organizacijskaJedinicaDto);
        }
        return gradDto;
    }

    @Override
    protected String getBasicSql() {
        return "SELECT e FROM Grad e";
    }

    public List<SelectItem> getGradListByZupanijaVelicina(String[] regije, String[] zupanije, String[] velicine) {
        List<SelectItem> resultSelectItems = new ArrayList<>();
        resultSelectItems.add(new SelectItem("", "Odaberite"));
        //sql
        String sql = "select grad.sifra, grad.naziv from online_events.grad grad ";
        if (regije != null && regije.length > 0) sql = sql + "join online_events.organizacijska_jedinica org_jed on org_jed.sifra = grad.org_jedinica ";
        sql = sql + "where 1 = 1 ";
        if (regije != null && regije.length > 0) sql = sql + "and org_jed.org_jedinica in :regije ";
        if (zupanije != null && zupanije.length > 0) sql = sql + "and grad.org_jedinica in :zupanije ";
        if (velicine != null && velicine.length > 0) sql = sql + "and grad.velicina in :velicine";

        //query
        Query query = getEntityManager().createNativeQuery(sql);
        if (regije != null && regije.length > 0) query.setParameter("regije", DogadajAppUtil.getIntegerFromStringList(regije));;
        if (zupanije != null && zupanije.length > 0) query.setParameter("zupanije", DogadajAppUtil.getIntegerFromStringList(zupanije));
        if (velicine != null && velicine.length > 0) query.setParameter("velicine", DogadajAppUtil.getIntegerFromStringList(velicine));

        //result
        List<Object[]> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            Stream<Object[]> listGradStream = resultList.stream();
            listGradStream.forEach(p -> resultSelectItems.add(new SelectItem(((BigInteger)p[0]).intValue(), (String)p[1])));
        }
        return resultSelectItems;
    }
}
