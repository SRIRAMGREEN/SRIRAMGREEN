package com.timesheet.module.client.repository;

import com.timesheet.module.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepo extends JpaRepository<Client,Integer> {

//    @Query(value = "Select * from client_entity", nativeQuery = true)
    List<Client> findAll();

}
