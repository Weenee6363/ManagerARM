package main.repositories;

import main.models.Contract;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Integer>
{
    List<Contract> findBySigningStatus(boolean status);
}
