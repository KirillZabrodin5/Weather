package dao;

import Utils.HibernateUtil;
import entity.User;
import exception.EntityExistsException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.Optional;

public class UserDao implements CrudDao<User, String> {
    static {
        HibernateUtil.addEntityToConfiguration(User.class);
    }

    public Optional<User> save(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(user);

            session.getTransaction().commit();

            return Optional.of(user);
        } catch (ConstraintViolationException e) {
            throw new EntityExistsException("Этот логин уже занят. Придумайте другой");
        }
    }

    public Optional<User> findById(String login) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();



            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = criteriaQuery.from(User.class);
            criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("login"), login));

            List<User> users = session.createQuery(criteriaQuery).getResultList();

            session.getTransaction().commit();

            if (!users.isEmpty()) {
                return Optional.of(users.get(0));
            } else {
                throw new EntityExistsException("Пользователя с таким логином не существует");
            }
        }
    }

    //этот метод понадобится в случаях, когда пользователь захочет сменить пароль
    public Optional<User> update(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User updateUser = session.merge(user);

            session.getTransaction().commit();

            return Optional.of(updateUser);
        } catch (HibernateException e) {
            throw new HibernateException("Не получилось обновить");
        }
    }

    //этот метод понадобится в случаях, когда пользователь захочет удалить свой аккаунт
    public Optional<User> delete(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
            return Optional.of(user);
        } catch (HibernateException e) {
            throw new HibernateException("Не получилось удалить");
        }
    }
}
