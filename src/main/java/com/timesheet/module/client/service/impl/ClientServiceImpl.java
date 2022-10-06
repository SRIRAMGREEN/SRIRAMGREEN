package com.timesheet.module.client.service.impl;

import com.timesheet.module.client.entity.Client;
import com.timesheet.module.client.dto.ClientDto;
import com.timesheet.module.client.repository.ClientRepo;
import com.timesheet.module.client.service.ClientService;
import com.timesheet.module.utils.NullPropertyName;
import com.timesheet.module.utils.exceptions.ServiceException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timesheet.module.utils.TimeSheetErrorCodes.*;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepo clientRepo;
    @Autowired
    ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public ClientDto addClient(Client client) {
        logger.info("ClientServiceImpl || addClientDetails ||Adding the client Details");
        try {
            clientRepo.save(client);
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            return modelMapper.map(client, ClientDto.class);
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Invalid data");
        }
    }

    @Override
    public ClientDto getClientDetail(int clientId) {
        try {
            Optional<Client> client = clientRepo.findById(clientId);
            if (client.isPresent()) {
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                ClientDto clientDto = modelMapper.map(client, ClientDto.class);
                logger.info("ClientServiceImpl || getClientDetail || Updating the client Info");
                return clientDto;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid request/Id not found");
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid data");
        }
    }


    @Override
    public List<ClientDto> getAllClientDetails() {
        logger.info("ClientServiceImpl || getAllClientDetails || Get all clients from the ClientEntity");
        try {
            Optional<List<Client>> clientList = Optional.ofNullable(clientRepo.findAll());
            List<ClientDto> clientDtoList = new ArrayList<>();
            if (clientList.isPresent()) {
                for (Client client : clientList.get()) {
                    ClientDto clientDto = modelMapper.map(client, ClientDto.class);
                    clientDtoList.add(clientDto);
                }
                return clientDtoList;
            } else {
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode());
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (ServiceException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "No data");
        } catch (Exception e) {
            throw new ServiceException(EXPECTATION_FAILED.getErrorCode(), "data not retrieved");
        }
    }

    @Override
    public ClientDto updateClientDetails(Client client) {
        try {
            ClientDto clientDto = new ClientDto();
            Optional<Client> client1 = clientRepo.findById(client.getClientId());
            if (client1.isPresent()) {
                BeanUtils.copyProperties(client, client1.get(), NullPropertyName.getNullPropertyNames(client));
                logger.info("ClientServiceImpl  || updateClientDetails || Data was updated client=={}", client);
                Client client2 = clientRepo.save(client1.get());
                BeanUtils.copyProperties(client2, clientDto, NullPropertyName.getNullPropertyNames(client2));
                return clientDto;
            } else {
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid ID or values");
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid data");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Data not saved");
        }
    }


    @Override
    public String deleteClient(int client_id) {
        logger.info("ClientServiceImpl || deleteClient || Client detail was deleted by particular ClientId=={}", client_id);
        try {
            clientRepo.deleteById(client_id);
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid Input");
        }
        return "Client Deleted Successfully";
    }
}