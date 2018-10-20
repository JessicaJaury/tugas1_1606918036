package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface InstansiService {
	List<InstansiModel> viewAll();
	InstansiModel findInstansiById(BigInteger id);
	List<InstansiModel> viewByProvinsi(ProvinsiModel provinsi);
	List<InstansiModel> viewByNama(String nama);
}
