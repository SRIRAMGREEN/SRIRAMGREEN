package com.timesheet.module.team.service.serviceimpl;

import com.timesheet.module.team.dto.TeamDto;
import com.timesheet.module.team.entity.Team;
import com.timesheet.module.team.repository.TeamRepo;
import com.timesheet.module.team.service.TeamService;
import com.timesheet.module.utils.exceptions.ServiceException;
import org.modelmapper.ModelMapper;
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
    public List<TeamDto> getAllTeamDetails() {
        logger.info("TeamServiceImpl || getAllTeamDetails || Get all clients from the TeamEntity");
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
                throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), DATA_NOT_FOUND.getErrorDesc());
            }
        } catch (NullPointerException e) {
            throw new ServiceException(EXPECTATION_FAILED.getErrorCode(), EXPECTATION_FAILED.getErrorDesc());
        } catch (ServiceException e) {
            throw new ServiceException(DATA_NOT_FOUND.getErrorCode(), DATA_NOT_FOUND.getErrorDesc());
        } catch (Exception e) {
            throw new ServiceException(INVALID_REQUEST.getErrorCode(), INVALID_REQUEST.getErrorDesc());
        }
    }
}
