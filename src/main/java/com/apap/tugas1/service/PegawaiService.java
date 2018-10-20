package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiByNip(String nip);
	PegawaiModel getPegawaiById(BigInteger id);
	void addPegawai(PegawaiModel pegawai);
	String generateNIP(PegawaiModel pegawai);
	List<PegawaiModel> viewAll();
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
	List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasuk(BigInteger instansi, Date tanggalLahir, String tahunMasuk);
}
