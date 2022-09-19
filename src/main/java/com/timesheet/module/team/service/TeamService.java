package com.timesheet.module.team.service;

import com.timesheet.module.team.entity.Team;
import com.timesheet.module.team.entity.dto.TeamDto;

import java.util.List;

public interface TeamService {
    TeamDto insertTeam(Team team);

    TeamDto getTeamDetails(int teamId);

    List<TeamDto> getAllTeamDetails();

    TeamDto updateTeam(Team team);

    String deleteTeam(int teamId);
}


