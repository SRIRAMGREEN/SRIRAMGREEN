package com.timesheet.module.team.entity;

import com.timesheet.module.Employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", unique = true)
    public int id;

    @Column(nullable = false, name = "teamName")
    public String teamName;

    @OneToMany(targetEntity = Employee.class,mappedBy = "team",fetch = FetchType.EAGER)
    private List<Employee>  employees;

}
