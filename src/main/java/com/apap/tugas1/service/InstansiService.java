package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface InstansiService {
	Optional<InstansiModel> findInstansiById(Long id);
	List<InstansiModel> viewAll();
	List<InstansiModel> viewByNama(String nama);
	List<InstansiModel> viewByProvinsi(ProvinsiModel provinsi);
}
