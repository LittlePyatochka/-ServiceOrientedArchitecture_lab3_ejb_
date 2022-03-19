package kamysh.repository;

import kamysh.entity.LoadStarship;
import kamysh.entity.Starship;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class StarshipRepository{

    private final SessionFactory sessionFactory;

    public StarshipRepository() {
        this.sessionFactory = SessionFactoryBuilder.getSessionFactory();
    }

    public void save(LoadStarship starship) {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {
            session.save(starship);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public Starship findById(Long id) {
        Session session = sessionFactory.openSession();
        return session.get(Starship.class, id);
    }

    public void delete(Long starshipiId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session
                    .createQuery("delete from kamysh.entity.LoadStarship where starship.id =: id")
                    .setParameter("id", starshipiId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public void deleteParatrooper(Long spaceMarineId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session
                    .createQuery("delete from kamysh.entity.LoadStarship where spaceMarineId =: id")
                    .setParameter("id", spaceMarineId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public Long getCountSpaceMarineInStarship(Long starshipiId) {
        return (Long) sessionFactory.openSession()
                .createQuery("select count(*) from kamysh.entity.LoadStarship where starship.id =: id")
                .setParameter("id", starshipiId)
                .getSingleResult();
    }

    public Long getStarshipIdBySpaceMarine(final Long spaceMarineId) {
        return (Long) sessionFactory.openSession()
                .createQuery("select starship.id from kamysh.entity.LoadStarship where spaceMarineId =: id")
                .setParameter("id", spaceMarineId)
                .getSingleResult();
    }
}
