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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        List<DemoModel> productsList = demoReporitory.findAll();
        if(!productsList.isEmpty()){
            for(DemoModel product : productsList){
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(DemoController.class).getProductById(id)).withSelfRel());
            }
        }
        return ResponseEntity.ok(productsList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable(value = "id") UUID id){
        Optional<DemoModel> productO = demoReporitory.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productO.get().add(linkTo(methodOn(DemoController.class).getAllProducts()).withRel("Product List"));
        return ResponseEntity.ok(productO.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid DemoRecordDto demoRecordDto){
        Optional<DemoModel> productO = demoReporitory.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var demoModel = productO.get();
        BeanUtils.copyProperties(demoRecordDto, demoModel);
        return ResponseEntity.ok(demoReporitory.save(demoModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id){
        Optional<DemoModel> productO = demoReporitory.findById(id);
        if(productO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        demoReporitory.delete(productO.get());
        return ResponseEntity.ok().body("Deleted product successfully");
    }


}
