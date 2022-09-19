//package com.timesheet.module.employetask.entity;
//
//import com.timesheet.module.Employee.entity.Employee;
//import com.timesheet.module.task.entity.Task;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "employee_task")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class EmployeeTask {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public int employeeTaskId;
//
//    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    @JoinColumn(name = "employee_Id", referencedColumnName = "id")
//    public Employee employee;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "task_id", referencedColumnName = "taskId", updatable = false)
//    public Task task;
//}
