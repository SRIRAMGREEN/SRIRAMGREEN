package com.timesheet.module.client.controller;

import com.timesheet.module.client.entity.Client;
import com.timesheet.module.client.dto.ClientDto;
import com.timesheet.module.client.service.ClientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/Client")

public class ClientController {

    @Autowired
    ClientService clientService;

    Logger logger = LoggerFactory.getLogger(ClientController.class);

    @PostMapping(value = "/insertClient")
    public ResponseEntity<ClientDto> insertClient(@RequestBody Client client) {
        logger.info("ClientEntity || insertClient || Inserting the Client Details from the ClientEntity");
        return new ResponseEntity<>(clientService.addClient(client), HttpStatus.OK);
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
    public ResponseEntity<ClientDto> updateClient(@RequestBody Client client) {
        logger.info("ClientEntity || updateClient || Updating the Client Details from the ClientEntity");
        return new ResponseEntity<>(clientService.updateClientDetails(client), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteClient")
    public String deleteClient(@RequestParam int clientId) {
        logger.info("ClientEntity || deleteClient || Deleting the Client Details from the ClientEntity");
        clientService.deleteClient(clientId);
        return "Client Details Deleted Successfully";
    }

    @PutMapping(value = "/insertImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ClientDto> insertImage(@RequestParam("image") Optional<MultipartFile> image, @RequestParam("id") int clientId) throws IOException {
        logger.info("ProjectController || insertImage || Inserting Images");
        return  new ResponseEntity<>(clientService.insertImage(image, clientId),HttpStatus.OK);


    }

}