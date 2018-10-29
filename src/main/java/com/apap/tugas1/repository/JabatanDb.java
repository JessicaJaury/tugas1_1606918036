package com.apap.tugas1.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;

@Repository
public interface JabatanDb extends JpaRepository<JabatanModel, Long>{
	Optional<JabatanModel> findById(Long id);
	
}
