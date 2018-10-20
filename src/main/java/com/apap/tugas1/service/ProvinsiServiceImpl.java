package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDb;

public class ProvinsiServiceImpl implements ProvinsiService{
	@Autowired
	private ProvinsiDb provinsiDb;

	@Override
	public List<ProvinsiModel> viewAll() {
		return provinsiDb.findAll();
	}

	@Override
	public ProvinsiModel findProvinsiById(BigInteger id) {
		return provinsiDb.findById(id);
	}
}
