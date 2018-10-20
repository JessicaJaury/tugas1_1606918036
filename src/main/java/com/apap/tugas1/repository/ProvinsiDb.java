package com.apap.tugas1.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.ProvinsiModel;

@Repository
public interface ProvinsiDb extends JpaRepository<ProvinsiModel, Long>{
	ProvinsiModel findById(BigInteger id);
}
