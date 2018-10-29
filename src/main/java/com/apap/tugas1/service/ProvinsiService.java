package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.ProvinsiModel;

public interface ProvinsiService {
	Optional<ProvinsiModel> findProvinsiById(Long id);
	List<ProvinsiModel> viewAll();
}
