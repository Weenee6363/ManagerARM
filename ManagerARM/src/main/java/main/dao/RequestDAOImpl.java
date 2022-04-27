package main.dao;

import main.models.Request;
import main.models.RequestStatus;
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
public class RequestDAOImpl implements RequestDAO
{

    private static final String REQUEST_STATUS_HQL = "SELECT r FROM Request r WHERE requestStatus = :requestStatus";

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addRequest(Request request)
    {
        getCurrentSession().save(request);
    }

    @Override
    public Request getRequest(int id)
    {
        return getCurrentSession().get(Request.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Request> findByRequestStatus(RequestStatus requestStatus)
    {
        Session session = getCurrentSession();
        Query<Request> requestQuery = session.createQuery(REQUEST_STATUS_HQL);
        requestQuery.setParameter("requestStatus", requestStatus);
        return requestQuery.list();
    }
}
