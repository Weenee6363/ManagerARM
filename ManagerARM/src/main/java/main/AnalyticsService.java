package main;

import main.dao.ContractDAO;
import main.dao.RequestDAO;
import main.models.Contract;
import main.models.Request;
import main.models.RequestStatus;

//Служба принятия решения по кредиту
public class AnalyticsService
{
    public static int makeDecision(Request request,
                                   RequestDAO requestDAO,
                                   ContractDAO contractDAO)
    {
        int contractId = 0;
        int random = (int) Math.ceil(Math.random() * 2);
        switch (random){
            case (1):
                request.setRequestStatus(RequestStatus.APPROVED);
                request.setPeriod(getPeriod());
                requestDAO.addRequest(request);

                //При одобрении кредита, автоматически создаётся договор
                Contract contract = new Contract();
                contract.setRequest(request);
                contract.setSigningStatus(false);
                contractDAO.addContract(contract);
                contractId = contract.getId();
                break;

            default:
                request.setRequestStatus(RequestStatus.DISAPPROVED);
                requestDAO.addRequest(request);
                break;
        }
        return contractId;
    }

    private static int getPeriod()
    {
        return (int)(30 + Math.random() * 336.0);
    }
}
