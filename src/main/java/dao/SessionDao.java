package dao;

import Utils.HibernateUtil;
import entity.SessionEntity;
import entity.User;
import exception.EntityExistsException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Optional;
import java.util.UUID;

public class SessionDao implements CrudDao<SessionEntity, UUID> {
    static {
        HibernateUtil.addEntityToConfiguration(SessionEntity.class);
    }

    public Optional<SessionEntity> save(SessionEntity sessionEntity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(sessionEntity);

            session.getTransaction().commit();
            return Optional.of(sessionEntity);
        } catch (ConstraintViolationException e) {
            throw new EntityExistsException("Этот логин уже занят. Придумайте другой");
        }
    }

    @Override
    public Optional<SessionEntity> findById(UUID uuid) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            SessionEntity sessionEntity = session.get(SessionEntity.class, uuid);

            session.getTransaction().commit();

            if (sessionEntity != null) {
                return Optional.of(sessionEntity);
            } else {
                throw new EntityExistsException("Пользователя с таким логином не существует");
            }
        } catch (HibernateException e) {
            throw new HibernateException("Не получилось обновить");
        }
    }

    @Override
    public Optional<SessionEntity> update(SessionEntity sessionEntity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            SessionEntity sessionEntity1 = session.merge(sessionEntity);

            session.getTransaction().commit();

            return Optional.of(sessionEntity1);
        } catch (HibernateException e) {
            throw new HibernateException("Не получилось обновить");
        }
    }

    @Override
    public Optional<SessionEntity> delete(SessionEntity sessionEntity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(sessionEntity);
            session.getTransaction().commit();
            return Optional.of(sessionEntity);
        } catch (HibernateException e) {
            throw new HibernateException("Не получилось удалить");
        }
    }
}
