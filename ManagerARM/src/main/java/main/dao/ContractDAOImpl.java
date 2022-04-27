package main.dao;

import main.models.Contract;
import main.models.Request;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ContractDAOImpl implements ContractDAO
{

    private static final String SIGNING_STATUS_HQL = "SELECT c FROM Contract c WHERE signingStatus = :signingStatus ";

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addContract(Contract Contract)
    {
        getCurrentSession().save(Contract);
    }

    @Override
    public Contract getContract(int id)
    {
        return getCurrentSession().get(Contract.class, id);
    }

    @Override
    public List<Contract> findBySigningStatus(boolean status)
    {
        Session session = getCurrentSession();
        Query<Contract> contractQuery = session.createQuery(SIGNING_STATUS_HQL);
        contractQuery.setParameter("signingStatus", status);
        return contractQuery.list();
    }

    @Override
    public void updateContract(Contract contract) {
        getCurrentSession().update(contract);
    }
}
