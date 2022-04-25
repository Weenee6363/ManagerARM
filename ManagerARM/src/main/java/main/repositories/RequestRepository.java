package main.repositories;

import main.models.Request;
import main.models.RequestStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request, Integer>
{
    List<Request> findByRequestStatus(RequestStatus requestStatus);
}
