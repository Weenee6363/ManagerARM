package main.dao;

import main.models.Contract;

import java.util.List;

public interface ContractDAO
{
    public void addContract(Contract Contract);
    public Contract getContract(int id);
    List<Contract> findBySigningStatus(boolean status);
    public void updateContract(Contract contract);
}
