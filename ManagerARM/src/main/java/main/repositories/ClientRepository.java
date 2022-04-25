package main.repositories;

import main.models.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer>
{
    Optional<Client> findFirstByFullNameAndPassportDetails(String fullName, String passportDetails);
    Optional<Client> findFirstByFullNameAndPassportDetailsAndPhoneNumber(String fullName, String passportDetail, String phoneNumber);
}
