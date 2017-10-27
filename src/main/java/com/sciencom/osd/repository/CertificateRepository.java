package com.sciencom.osd.repository;

import com.sciencom.osd.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, String> {
}
