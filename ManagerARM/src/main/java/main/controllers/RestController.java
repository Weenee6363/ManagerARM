package main.controllers;

import main.models.Client;
import main.models.Contract;
import main.repositories.ClientRepository;
import main.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
@RequestMapping()
public class RestController
{
    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ClientRepository clientRepository;

    //Подписание договора
    @PatchMapping("/contract/{id}")
    public boolean contractSigning(@PathVariable int id)
    {
        Optional<Contract> optionalContract = contractRepository.findById(id);
        if(optionalContract.isPresent())
        {
            Contract contract = optionalContract.get();
            contract.setSigningStatus(true);
            contract.setSigningDate(new Date());
            contractRepository.save(contract);
            return true;
        }
        return false;
    }

    //Поиск клиента по имени, номеру и паспорту
    @GetMapping("/client")
    public Client getClient(String fullName, String passportDetails, String phoneNumber)
    {
        Optional<Client> optionalClient  = clientRepository.
                findFirstByFullNameAndPassportDetailsAndPhoneNumber(fullName, passportDetails, phoneNumber);

        return optionalClient.orElse(null);
    }

}
