package com.timesheet.module.client.service;

import com.timesheet.module.client.entity.Client;
import com.timesheet.module.client.dto.ClientDto;

import java.util.List;

public interface ClientService {

    ClientDto addClient(Client client);

    ClientDto getClientDetail(int clientId);

    List<ClientDto> getAllClientDetails();

    ClientDto updateClientDetails(Client client);

    String deleteClient(int clientId);


}
