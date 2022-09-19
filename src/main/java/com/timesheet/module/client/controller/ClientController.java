package com.timesheet.module.client.controller;

import com.timesheet.module.client.entity.Client;
import com.timesheet.module.client.entity.dto.ClientDto;
import com.timesheet.module.client.service.ClientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Client")
public class ClientController {

    @Autowired
    ClientService clientService;

    Logger logger = LoggerFactory.getLogger(ClientController.class);


    @PostMapping(value = "/insertClient")
    public ResponseEntity<ClientDto> insertClient(@RequestBody Client clientEntity) {
        logger.info("ClientEntity || insertClient || Inserting the Client Details from the ClientEntity");
        return new ResponseEntity<>(clientService.addClient(clientEntity), HttpStatus.OK);
    }
    @GetMapping(value = "/getClientDetails")
    public ResponseEntity<ClientDto> getClient(@RequestParam int clientId) {
        logger.info("ClientEntity || getClient || Getting the Client Details from the ClientEntity");
        return new ResponseEntity<>(clientService.getClientDetail(clientId), HttpStatus.OK);
    }
    @GetMapping(value = "/getAllClientDetails")
    public ResponseEntity<List<ClientDto>> getAllClient() {
        logger.info("ClientEntity || getClient || Getting the Client Details from the ClientEntity");
        return new ResponseEntity<>(clientService.getAllClientDetails(), HttpStatus.OK);
    }

    @PutMapping(value = "/updateClient")
    public ResponseEntity<ClientDto> updateClient(@RequestBody Client clientEntity) {
        logger.info("ClientEntity || updateClient || Updating the Client Details from the ClientEntity");
        return new ResponseEntity<>(clientService.updateClientDetails(clientEntity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteClient")
    public void deleteClient(@RequestParam int clientId) {
        logger.info("ClientEntity || deleteClient || Deleting the Client Details from the ClientEntity");
        clientService.deleteClient(clientId);
    }
}