package com.apap.tugas1.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

@Repository
public interface InstansiDb extends JpaRepository<InstansiModel, Long>{
	Optional<InstansiModel> findById(Long id);
	List<InstansiModel> findByNama(String nama);
	List<InstansiModel> findByProvinsi(ProvinsiModel provinsi);
}
