package main.controllers;

import main.AnalyticsService;
import main.models.Client;
import main.models.Contract;
import main.models.Request;
import main.models.RequestStatus;
import main.repositories.ClientRepository;
import main.repositories.ContractRepository;
import main.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ViewController
{

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private RequestRepository requestRepository;

    //Форма создания заявки
    @GetMapping("/request/new")
    public String makeRequest(Model model)
    {
        model.addAttribute("client", new Client());
        return "new_request";
    }

    //Сохранение созданной заявки и получение информации о её статусе
    @PostMapping("/request/info")
    public String createRequest(Client client, int creditAmount, Model model)
    {
        //Сперва проверим, есть ли такой клиент в базе.
        //Если клиент с таким именем и паспортными данными уже есть в бд,
        //то, обновим его данные (номер телефона, адрес и т.п.)

        Optional<Client> clientOptional = clientRepository.findFirstByFullNameAndPassportDetails(
                client.getFullName(),
                client.getPassportDetails());

        if(clientOptional.isPresent())
        {
            Client actualClient=clientOptional.get();

            //Выставляем ID клента, найденного в базе, для клиента, полученного из заявки,
            //чтобы не создавать нового, а обновить старого
            client.setId(actualClient.getId());
        }

        clientRepository.save(client);

        Request request = new Request();
        request.setClient(client);
        request.setCreditAmount(creditAmount);

        //Отправляем заявку на рассмотрение,
        // если заявка будет одобрена, получим id договора, который необходимо подписать
        int contractId = AnalyticsService.makeDecision(request, requestRepository, contractRepository);

        if(contractId > 0)
        {
            model.addAttribute("request", request);
            model.addAttribute("contractId", contractId);
            return "request_info";
        }

        return "not_approved";
    }

    //Получение списка всех клиентов
    @GetMapping("/clients")
    public String getClients(Model model)
    {
        Iterable<Client> clientIterable = clientRepository.findAll();
        ArrayList<Client> clients = new ArrayList<>();
        for (Client client : clientIterable)
        {
            clients.add(client);
        }
        model.addAttribute("clients", clients);
        return "clients";
    }

    //Список подписанных договоров
    @GetMapping("/signed_contracts")
    public String getSignedContracts(Model model)
    {
        ArrayList<Contract> contracts = (ArrayList<Contract>) contractRepository.findBySigningStatus(true);
        model.addAttribute("contracts", contracts);
        return "contracts";
    }

    //Список одобренных заявок
    @GetMapping("/approved_requests")
    public String getApprovedRequests(Model model)
    {
        ArrayList<Request> requests = (ArrayList<Request>) requestRepository.findByRequestStatus(RequestStatus.APPROVED);
        model.addAttribute("requests", requests);
        return "requests";
    }
}
