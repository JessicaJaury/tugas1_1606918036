package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDb;

public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDb instansiDb;

	@Override
	public List<InstansiModel> viewAll() {
		return instansiDb.findAll();
	}

	@Override
	public InstansiModel findInstansiById(BigInteger id) {
		return instansiDb.findById(id);
	}

	@Override
	public List<InstansiModel> viewByProvinsi(ProvinsiModel provinsi) {
		return instansiDb.findByProvinsi(provinsi);
	}

	@Override
	public List<InstansiModel> viewByNama(String nama) {
		return instansiDb.findByNama(nama);
	}
}
