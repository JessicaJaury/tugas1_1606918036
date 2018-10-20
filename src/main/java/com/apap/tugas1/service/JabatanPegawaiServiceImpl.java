package com.apap.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanPegawaiDb;

public class JabatanPegawaiServiceImpl implements JabatanPegawaiService{
	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;

	@Override
	public int countPegawaiByJabatan(JabatanModel jabatan) {
//		return jabatanPegawaiDb.findByIdJabatan(jabatan.getId()).size();
		
		return 0;
	}
}
