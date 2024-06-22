package dao;

import Utils.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.Optional;

public interface CrudDao<T, ID> {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    Optional<T> save(T t);

    Optional<T> findById(ID id);

    Optional<T> update(T t);

    Optional<T> delete(T t);
}
