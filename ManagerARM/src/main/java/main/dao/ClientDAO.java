package main.dao;

import main.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDAO
{
    public void addClient(Client client);
    public void updateClient(Client client);
    public Client getClient(int id);
    public List<Client> getAllClients();
    public Client findClientByNameAndPassportAndPhoneNumber(String name, String passport, String phoneNumber);
    public Client findClientByNameAndPassport(String name, String passport);
}
