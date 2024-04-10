package com.example.demoCRUD.repositories;


import com.example.demoCRUD.models.DemoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DemoReporitory extends JpaRepository<DemoModel, UUID> {

}
