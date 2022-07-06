
package com.arukione.curriculum_design.controller;

import com.arukione.curriculum_design.service.DeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeanController {
    final DeanService deanService;
    @Autowired
    DeanController(DeanService deanService){
        this.deanService=deanService;
    }



}

