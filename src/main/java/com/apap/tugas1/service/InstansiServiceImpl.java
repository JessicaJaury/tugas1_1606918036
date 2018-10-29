package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDb;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDb instansiDb;

	@Override
	public Optional<InstansiModel> findInstansiById(Long id) {
		return instansiDb.findById(id);
	}
	
	@Override
	public List<InstansiModel> viewAll() {
		return instansiDb.findAll();
	}

	@Override
	public List<InstansiModel> viewByNama(String nama) {
		return instansiDb.findByNama(nama);
	}
	
	@Override
	public List<InstansiModel> viewByProvinsi(ProvinsiModel provinsi) {
		return instansiDb.findByProvinsi(provinsi);
	}
}
