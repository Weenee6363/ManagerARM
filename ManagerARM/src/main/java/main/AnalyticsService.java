package main;

import main.models.Contract;
import main.models.Request;
import main.models.RequestStatus;
import main.repositories.ContractRepository;
import main.repositories.RequestRepository;

//Служба принятия решения по кредиту
public class AnalyticsService
{
    public static int makeDecision(Request request,
                                   RequestRepository requestRepository,
                                   ContractRepository contractRepository)
    {
        int contractId = 0;
        int random = (int) Math.ceil(Math.random() * 2);
        switch (random){
            case (1):
                request.setRequestStatus(RequestStatus.APPROVED);
                request.setPeriod(getPeriod());
                request = requestRepository.save(request);

                //При одобрении кредита, автоматически создаётся договор
                Contract contract = new Contract();
                contract.setRequest(request);
                contract.setSigningStatus(false);
                contract = contractRepository.save(contract);
                contractId = contract.getId();
                break;

            default:
                request.setRequestStatus(RequestStatus.DISAPPROVED);
                request = requestRepository.save(request);
                break;
        }
        return contractId;
    }

    private static int getPeriod()
    {
        return (int)(30 + Math.random() * 336.0);
    }
}
