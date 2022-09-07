package com.timesheet.entity.model;

import com.timesheet.entity.utils.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team extends DateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id",unique = true,nullable = false)
    public int teamId;

    @Column(name = "team_name")
    public String teamName;
}
