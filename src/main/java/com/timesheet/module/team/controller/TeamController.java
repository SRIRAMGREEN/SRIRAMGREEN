package com.timesheet.module.team.controller;

import com.timesheet.module.team.dto.TeamDto;
import com.timesheet.module.team.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    Logger logger = LoggerFactory.getLogger(TeamController.class);

    @GetMapping(value = "/getAllTeams")
    public ResponseEntity<List<TeamDto>> getAllClient() {
        logger.info("ClientEntity || getClient || Getting the Client Details from the ClientEntity");
        return new ResponseEntity<>(teamService.getAllTeamDetails(), HttpStatus.OK);
    }

}
