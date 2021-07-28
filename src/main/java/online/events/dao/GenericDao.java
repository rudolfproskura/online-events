package online.events.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDao<E, T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Returns entity from DTO
     */
    protected abstract E formEntity(T dto);

    /**
     * Returns DTO from entity
     */
    protected abstract T formDTO(E entity);

    /**
     * Return Select SQL for entity
     */
    protected abstract String getBasicSql();


    /*
     * Returns list of entities (entites transfor to dto)
     */
    public List<T> findAll() {
        List<E> listEntity = getEntityManager().createQuery(getBasicSql()).getResultList();
        return formListDto(listEntity);
        //return formListDto(null);
    }

    /*
     * Returns stream of entities (entites transfor to dto) //TODO Stream result
     */
//    public Stream<T> findAll() {
//        List<E> listEntity = getEntityManager().createQuery(getBasicSql(), E).get;
//        return formListDto(listEntity);
//    }

    /**
     * Returns list of entities (entites transfor to dto) .
     *
     * By default used in findAll method.
     *
     */
    public List<T> formListDto(List<E> listEntity) {
        List<T> listDto = new ArrayList<>();
        if (listEntity != null && !listEntity.isEmpty()) {
            for (E e : listEntity) {
                listDto.add(formDTO(e));
            }
        }
        return listDto;
    }


//GENERIC MIN DAO
//    public List<T> listEntity = getEntityManager()getAll();
//
//    void save(T t);
//
//    void update(T t, String[] params);
//
//    void delete(T t);

}
