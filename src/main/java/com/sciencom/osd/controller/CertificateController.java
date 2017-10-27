package com.sciencom.osd.controller;

import com.sciencom.osd.entity.Certificate;
import com.sciencom.osd.entity.Response;
import com.sciencom.osd.enums.RoleEnum;
import com.sciencom.osd.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CertificateController extends BaseController {

    @Autowired
    private CertificateService certificateService;

    // create
    @PostMapping(value = "/certificate")
    public ResponseEntity create(@RequestHeader("Authorization") String token,
                                 @RequestBody Certificate certificate){
        // authorisasi
        if (!authorize(RoleEnum.MANAGER, token)){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        Response created = certificateService.create(certificate);
        return getHttpResponse(created);
    }

    // update
    @PutMapping("/certificate")
    public ResponseEntity update(@RequestBody Certificate certificate){
        Response updated = certificateService.update(certificate);
        return getHttpResponse(updated);
    }

    // get one
    @GetMapping("/certificate/{id}")
    public ResponseEntity getOne(@PathVariable String id){
        Certificate certificate = certificateService.getOne(id);
        return new ResponseEntity(certificate, HttpStatus.OK);
    }

    // get all
    @GetMapping("/certificate")
    public ResponseEntity getAll(Pageable pageable){
        Page<Certificate> certificates = certificateService.getAll(pageable);
        return new ResponseEntity(certificates, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/certificate/{id}")
    public ResponseEntity delete(@PathVariable String id){
        boolean result = certificateService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
