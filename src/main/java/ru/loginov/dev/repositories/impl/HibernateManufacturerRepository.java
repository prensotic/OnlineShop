package ru.loginov.dev.repositories.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.loginov.dev.entities.Manufacturer;
import ru.loginov.dev.repositories.ManufacturerRepository;

import java.util.List;

@Repository
public class HibernateManufacturerRepository implements ManufacturerRepository {
    private final SessionFactory sessionFactory;

    public HibernateManufacturerRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Manufacturer> getAllManufacturer() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("select m from Manufacturer m", Manufacturer.class)
                .getResultList();
    }

    @Override
    public Manufacturer findManufacturerById(long id) {
        return sessionFactory
                .getCurrentSession()
                .get(Manufacturer.class, id);
    }

    @Override
    public Manufacturer findManufacturerByName(String manufacturerName) {
        return sessionFactory
                .getCurrentSession()
                .createQuery(
                        "select m from Manufacturer m " +
                                "where m.manufacturerName = :manufacturerName", Manufacturer.class)
                .setParameter("manufacturerName", manufacturerName)
                .uniqueResult();
    }

    @Override
    public boolean existsManufacturerName(String manufacturerName) {
        Long count = sessionFactory
                .getCurrentSession()
                .createQuery(
                        "select count(m) from Manufacturer m " +
                                "where m.manufacturerName = :manufacturerName", Long.class)
                .setParameter("manufacturerName", manufacturerName)
                .uniqueResult();

        return count > 0;
    }

    @Override
    public boolean existsManufacturerNameAndIdNot(String manufacturerName, long id) {
        Long count = sessionFactory
                .getCurrentSession()
                .createQuery("select count(m) from Manufacturer m " +
                                "where m.manufacturerName = :manufacturerName " +
                                "and m.id <> :id",
                        Long.class)
                .setParameter("manufacturerName", manufacturerName)
                .setParameter("id", id)
                .uniqueResult();

        return count > 0;
    }

    @Override
    public void saveManufacturer(Manufacturer manufacturer) {
        sessionFactory.getCurrentSession().persist(manufacturer);
    }

    @Override
    public void updateManufacturer(Manufacturer manufacturer) {
        sessionFactory.getCurrentSession().merge(manufacturer);
    }

    @Override
    public void deleteManufacturer(long id) {
        Manufacturer manufacturer = findManufacturerById(id);
        if(manufacturer != null) sessionFactory.getCurrentSession().remove(manufacturer);
    }
}