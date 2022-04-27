package main.controllers;

import main.dao.ClientDAO;
import main.dao.ContractDAO;
import main.models.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@org.springframework.web.bind.annotation.RestController
@RequestMapping()
public class RestController
{
    @Autowired
    private ContractDAO contractDAO;

    //Подписание договора
    @PatchMapping("/contract/{id}")
    public boolean contractSigning(@PathVariable int id)
    {
       Contract contract = contractDAO.getContract(id);
        if(contract != null)
        {
            contract.setSigningStatus(true);
            contract.setSigningDate(new Date());
            contractDAO.updateContract(contract);
            return true;
        }
        return false;
    }

}
