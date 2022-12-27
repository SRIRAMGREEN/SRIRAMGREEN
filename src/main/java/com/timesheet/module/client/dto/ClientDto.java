package com.timesheet.module.client.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {

    private int clientId;

    private String clientName;

    private byte[] image;

}
