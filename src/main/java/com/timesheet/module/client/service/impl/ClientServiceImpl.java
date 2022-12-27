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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timesheet.module.utils.TimeSheetErrorCodes.*;
import static com.timesheet.module.utils.TimesheetConstants.IMAGE_REGEX;

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
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), INVALID_REQUEST.getErrorDesc());
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), DATA_NOT_SAVED.getErrorDesc());
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
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), DATA_NOT_FOUND.getErrorDesc());
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), INVALID_REQUEST.getErrorDesc());
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
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), INVALID_REQUEST.getErrorDesc());
        } catch (ServiceException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), DATA_NOT_FOUND.getErrorDesc());
        } catch (Exception e) {
            throw new ServiceException(EXPECTATION_FAILED.getErrorCode(), EXPECTATION_FAILED.getErrorDesc());
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
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), DATA_NOT_FOUND.getErrorDesc());
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), INVALID_REQUEST.getErrorDesc());
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), DATA_NOT_SAVED.getErrorDesc());
        }
    }


    @Override
    public void deleteClient(int client_id) {
        logger.info("ClientServiceImpl || deleteClient || Client detail was deleted by particular ClientId=={}", client_id);
        try {
            clientRepo.deleteById(client_id);
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), INVALID_REQUEST.getErrorDesc());
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), DATA_NOT_FOUND.getErrorDesc());
        }
    }

    @Transactional
    @Override
    public String insertImage(Optional<MultipartFile> image, int clientId) {
        Optional<Client> client = clientRepo.findById(clientId);
        if (client.isPresent()) {
            try {
                Client clients = client.get();
                if (image.isPresent()) {
                    clients.setImage(image.get().getBytes());
                    clientRepo.save(clients);
                } else {
                    throw new NullPointerException();
                }
            } catch (NullPointerException e) {
                throw new ServiceException(INVALID_REQUEST.getErrorCode());
            } catch (Exception e) {
                throw new ServiceException(IMAGE_REGEX, "Invalid image format");
            }
        } else {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid data");
        }
        return "Client Image Inserted Successfully";
    }
}