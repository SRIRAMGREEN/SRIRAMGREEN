package com.timesheet.module.team.controller;

import com.timesheet.module.team.entity.Team;
import com.timesheet.module.team.entity.dto.TeamDto;
import com.timesheet.module.team.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    Logger logger = LoggerFactory.getLogger(TeamController.class);

    @PostMapping(value = "/insertTeam")
    public ResponseEntity<TeamDto> insertClient(@RequestBody Team team) {
        logger.info("TeamController || insertTeam || Inserting the Team Details from the TeamEntity");
        return new ResponseEntity<>(teamService.insertTeam(team), HttpStatus.OK);
    }

    @GetMapping(value = "/getTeamDetails")
    public ResponseEntity<TeamDto> getTeamDetails(@RequestParam int teamId) {
        logger.info("TeamController || getTeamDetails || getting the TeamDetails from the TeamEntity");
        return new ResponseEntity<>(teamService.getTeamDetails(teamId), HttpStatus.OK);
    }


    @GetMapping(value = "/getAllTeamDetails")
    public ResponseEntity<List<TeamDto>> getTeamList() {
        logger.info("TeamController || getTeam|| Getting the Team Details from the TeamEntity");
        return new ResponseEntity<>(teamService.getAllTeamDetails(), HttpStatus.OK);
    }

    @PutMapping(value = "/updateTeam")
    public ResponseEntity<TeamDto> updateClient(@RequestBody Team team) {
        logger.info("TeamController || updateTeam  || Updating the Team Details from the TeamEntity");
        return new ResponseEntity<>(teamService.updateTeam(team), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteTeam")
    public void deleteTeam(@RequestParam int teamId) {
        logger.info("TeamController || deleteTeam || Deleting the Team Details from the TeamEntity");
        teamService.deleteTeam(teamId);
    }
}