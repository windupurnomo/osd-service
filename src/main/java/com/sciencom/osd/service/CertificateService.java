package com.sciencom.osd.service;

import com.sciencom.osd.entity.Certificate;
import com.sciencom.osd.entity.Response;
import com.sciencom.osd.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    // create
    public Response create(Certificate certificate){
        if (certificate.getIssuer() == null || certificate.getIssuer().isEmpty())
            return new Response("Issuer tidak boleh kosong");
        if (certificate.getName() == null || certificate.getName().isEmpty())
            return new Response("Name tidak boleh kosong");
        if (certificate.getType() == null)
            return new Response("Type tidak boleh kosong");

        String id = UUID.randomUUID().toString();
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.YEAR, 2);
        Date exp = c.getTime();

        certificate.setId(id);
        certificate.setPublishedDate(now);
        certificate.setExpired(exp);
        certificateRepository.save(certificate);
        return new Response(certificate);
    }

    // update
    public Response update(Certificate param){
        if (param.getIssuer() == null || param.getIssuer().isEmpty())
            return new Response("Issuer tidak boleh kosong");
        if (param.getName() == null || param.getName().isEmpty())
            return new Response("Name tidak boleh kosong");
        if (param.getType() == null)
            return new Response("Type tidak boleh kosong");

        Certificate certificate = certificateRepository.findOne(param.getId());
        certificate.setIssuer(param.getIssuer());
        certificate.setName(param.getName());
        certificate.setDescription(param.getDescription());
        certificate.setType(param.getType());
        certificateRepository.save(certificate);
        return new Response(certificate);
    }

    // get one
    public Certificate getOne(String id){
        return certificateRepository.findOne(id);
    }

    // get all
    public Page<Certificate> getAll(Pageable pageable){
        return certificateRepository.findAll(pageable);
    }

    // delete
    public boolean delete(String id){
        Certificate certificate = certificateRepository.findOne(id);
        if (certificate == null) return false;
        certificateRepository.delete(id);
        Certificate x = certificateRepository.findOne(id);
        return x == null;
    }

}
