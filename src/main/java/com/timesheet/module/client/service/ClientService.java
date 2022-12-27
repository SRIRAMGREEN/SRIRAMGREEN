package com.timesheet.module.client.service;

import com.timesheet.module.client.entity.Client;
import com.timesheet.module.client.dto.ClientDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    ClientDto addClient(Client client);

    ClientDto getClientDetail(int clientId);

    List<ClientDto> getAllClientDetails();

    ClientDto updateClientDetails(Client client);

    void deleteClient(int clientId);


    @Transactional
    String insertImage(Optional<MultipartFile> image, int clientId);
}
