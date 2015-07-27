package ua.com.csltd.server.dao.coolbtt;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import ua.com.csltd.common.models.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author : verner
 * @since : 21.07.2015
 */
public class BaseCoolDAO<T extends BaseEntity> {

    @PersistenceContext(unitName = "coolPersistenceUnit")
    protected EntityManager entityManager;

    private Class<T> persistentClass;

    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @SuppressWarnings("unchecked")
    public BaseCoolDAO(Class<T> clazz) {
        persistentClass = clazz;
    }

    public T findById(final Long id) {
        return entityManager.find(persistentClass, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("from " + persistentClass.getName() + " order by id asc").getResultList();
    }

    public void persist(final T entity) {
        entityManager.persist(entity);
    }

    public void persistAll(final List<T> entityList) {
        for (T entity : entityList) {
            persist(entity);
        }
    }

    public void saveAll(List<T> entityList) {
        for (T entity : entityList) {
            getSession().saveOrUpdate(entity);
        }
    }

    public T update(final T entity) {
        return entityManager.merge(entity);
    }

    public void delete(final T entity) {
        doDelete(entity);
    }

    public void delete(final Long entityId) {
        final T entity = findById(entityId);
        doDelete(entity);
    }

    private void doDelete(final T entity) {
        entityManager.remove(entity);
    }
}
