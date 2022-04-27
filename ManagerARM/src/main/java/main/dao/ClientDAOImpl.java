package main.dao;

import main.models.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ClientDAOImpl implements ClientDAO
{
    private static final String NAME_PASSPORT_NUMBER_HQL = "SELECT c FROM Client c WHERE c.fullName = :name AND " +
                                                                  "c.passportDetails = :passport AND " +
                                                                  "c.phoneNumber = :number";

    private static final String NAME_PASSPORT_HQL = "SELECT c FROM Client c WHERE c.fullName = :name AND " +
                                                                          "c.passportDetails = :passport" ;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addClient(Client client)
    {
        getCurrentSession().save(client);
    }

    @Override
    public void updateClient(Client client)
    {
        getCurrentSession().update(client);
    }

    @Override
    public Client getClient(int id)
    {
        return getCurrentSession().get(Client.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Client> getAllClients() {
        return getCurrentSession().createQuery("from Client").list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Client findClientByNameAndPassportAndPhoneNumber(String name, String passport, String phoneNumber)
    {
        Session session = getCurrentSession();
        Query<Client> query = session.createQuery(NAME_PASSPORT_NUMBER_HQL);
        query.setParameter("name", name);
        query.setParameter("passport", passport);
        query.setParameter("number", phoneNumber);
        Optional<Client> client = query.stream().findFirst();
        return client.orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Client findClientByNameAndPassport(String name, String passport) {
        Session session = getCurrentSession();
        Query<Client> query = session.createQuery(NAME_PASSPORT_HQL);
        query.setParameter("name", name);
        query.setParameter("passport", passport);
        Optional<Client> client = query.stream().findFirst();
        return client.orElse(null);
    }
}
