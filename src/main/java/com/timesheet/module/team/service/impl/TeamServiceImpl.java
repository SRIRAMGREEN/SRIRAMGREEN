package com.timesheet.module.team.service.impl;

import com.timesheet.module.team.entity.Team;
import com.timesheet.module.team.entity.dto.TeamDto;
import com.timesheet.module.team.repository.TeamRepo;
import com.timesheet.module.team.service.TeamService;
import com.timesheet.module.utils.exceptions.ServiceException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timesheet.module.utils.TimeSheetErrorCodes.*;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRepo teamRepo;

    @Autowired
    ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);

    @Override
    public TeamDto insertTeam(Team team) {
        try {
            Team team1 = teamRepo.save(team);
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            TeamDto teamDto = modelMapper.map(team1, TeamDto.class);
            logger.info("TeamServiceImpl|| insertData || Inserting the Team list: {}", teamDto);
            return teamDto;
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode());
        }
    }

    @Override
    public TeamDto getTeamDetails(int teamId) {
        try {
            Optional<Team> team = teamRepo.findById(teamId);
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            TeamDto teamDto = modelMapper.map(team, TeamDto.class);
            if (team.isPresent()) {
                return teamDto;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Invalid req/Id not found");
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid req");
        }
    }

    @Override
    public List<TeamDto> getAllTeamDetails() {
        logger.info("TeamServiceImpl || getTeam || Get all Teams from the TeamEntity");
        try {
            Optional<List<Team>> teamList = Optional.ofNullable(teamRepo.findAll());
            List<TeamDto> teamDtoList = new ArrayList<>();
            if (!teamList.get().isEmpty()) {
                for (Team team : teamList.get()) {
                    TeamDto teamDto = modelMapper.map(team, TeamDto.class);
                    teamDtoList.add(teamDto);
                }
                return teamDtoList;
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
    public TeamDto updateTeam(Team team) {
        try {
            TeamDto teamDto = modelMapper.map(team, TeamDto.class);
            Optional<Team> teamData = teamRepo.findById(teamDto.getTeamId());
            if (teamData.isPresent()) {
                logger.info("ProjectServiceImpl || updateData || Updating the  Project");
                Team teams = teamRepo.save(teamData.get());
                modelMapper.map(team, TeamDto.class);
                return teamDto;
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request/Id not found");
        } catch (Exception e) {
            throw new ServiceException(DATA_NOT_SAVED.getErrorCode(), "Invalid data");
        }
    }

    @Override
    public String deleteTeam(int teamId) {
        Optional<Team> team = teamRepo.findById(teamId);
        if (team.isPresent()) {
            logger.info("ProjectServiceImpl || deleteData || Deleting the project id: {}", teamId);
            try {
                teamRepo.deleteById(teamId);

            } catch (NullPointerException e) {
                throw new ServiceException(INVALID_REQUEST.getErrorCode(), "Invalid request");
            } catch (Exception e) {
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), "Data Not Found");
            }
        }
        return "deleted";
    }
}