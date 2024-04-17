package com.example.demoCRUD.controllers;


import com.example.demoCRUD.dtos.DemoRecordDto;
import com.example.demoCRUD.models.DemoModel;
import com.example.demoCRUD.repositories.DemoReporitory;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class DemoController {

    @Autowired
    DemoReporitory demoReporitory;

    @PostMapping("/products")
    public ResponseEntity<DemoModel> saveProduct(@RequestBody @Valid DemoRecordDto demoRecordDto){
        var demoModel = new DemoModel();
        BeanUtils.copyProperties(demoRecordDto, demoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(demoReporitory.save(demoModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<DemoModel>> getAllProducts(){
        return ResponseEntity.ok(demoReporitory.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable(value = "id") UUID id){
        Optional<DemoModel> productO = demoReporitory.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.ok(productO.get());
    }
}
