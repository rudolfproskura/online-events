package online.events.repository;

import online.events.model.TipKorisnika;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Transactional(Transactional.TxType.SUPPORTS)
public class TipKorisnikaRepository {

    @PersistenceContext(unitName = "OnlineEventsPU")
    private EntityManager entityManager;

    public TipKorisnika find(@NotNull Integer sifra) {
        return entityManager.find(TipKorisnika.class, sifra);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TipKorisnika create(@NotNull TipKorisnika tipKorisnika) {
        entityManager.persist(tipKorisnika);
        return tipKorisnika;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void delete(@NotNull Integer sifra) {
        entityManager.remove(entityManager.getReference(TipKorisnika.class, sifra));
    }

}
