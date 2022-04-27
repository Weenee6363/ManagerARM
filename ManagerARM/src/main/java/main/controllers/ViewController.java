package main.controllers;

import main.AnalyticsService;
import main.dao.ClientDAO;
import main.dao.ContractDAO;
import main.dao.RequestDAO;
import main.models.Client;
import main.models.Contract;
import main.models.Request;
import main.models.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class ViewController
{
    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private ContractDAO contractDAO;

    @Autowired
    private RequestDAO requestDAO;


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

        Client clientOptional = clientDAO.findClientByNameAndPassport(
                client.getFullName(),
                client.getPassportDetails());

        if(clientOptional != null)
        {
            //Выставляем ID клента, найденного в базе, для клиента, полученного из заявки,
            //чтобы не создавать нового, а обновить старого
            client.setId(clientOptional.getId());
            clientDAO.updateClient(client);
        }
        else
        {
            clientDAO.addClient(client);
        }

        Request request = new Request();
        request.setClient(client);
        request.setCreditAmount(creditAmount);

        //Отправляем заявку на рассмотрение,
        // если заявка будет одобрена, получим id договора, который необходимо подписать
        int contractId = AnalyticsService.makeDecision(request, requestDAO, contractDAO);

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
        Iterable<Client> clientIterable = clientDAO.getAllClients();
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
        ArrayList<Contract> contracts = (ArrayList<Contract>) contractDAO.findBySigningStatus(true);
        model.addAttribute("contracts", contracts);
        return "contracts";
    }

    //Список одобренных заявок
    @GetMapping("/approved_requests")
    public String getApprovedRequests(Model model)
    {
        ArrayList<Request> requests = (ArrayList<Request>) requestDAO.findByRequestStatus(RequestStatus.APPROVED);
        model.addAttribute("requests", requests);
        return "requests";
    }

    //Отображаем найденного клиента или говорим, что клиент не найден
    @GetMapping("/client")
    public String getClient(@RequestParam("name") String fullName,
                            @RequestParam("passport") String passportDetails,
                            @RequestParam("number") String phoneNumber,
                            Model model)
    {
        Client client = clientDAO.findClientByNameAndPassportAndPhoneNumber(fullName, passportDetails, phoneNumber);
        if (client != null)
        {
            model.addAttribute("client", client);
            return "client";
        }
        return "client_not_found";
    }

    //Форма для поиска клиента по имени, номеру и паспорту
    @GetMapping("/search")
    public String searchClient(Model model)
    {
        model.addAttribute("name", new String());
        model.addAttribute("passport", new String());
        model.addAttribute("number", new String());
        return "search_client";
    }
}
