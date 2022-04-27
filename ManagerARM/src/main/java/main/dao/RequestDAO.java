package main.dao;

import main.models.Request;
import main.models.RequestStatus;

import java.util.List;

public interface RequestDAO
{
    public void addRequest(Request request);
    public Request getRequest(int id);
    List<Request> findByRequestStatus(RequestStatus requestStatus);
}
