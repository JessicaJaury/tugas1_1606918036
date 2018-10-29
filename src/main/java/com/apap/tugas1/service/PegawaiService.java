package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	Optional<PegawaiModel> getPegawaiById(Long id);
	PegawaiModel getPegawaiByNip(String nip);
	void addPegawai(PegawaiModel pegawai);
	String setNIP(PegawaiModel pegawai);
	List<PegawaiModel> viewAll();
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
	//List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasuk(BigInteger instansi, Date tanggalLahir, String tahunMasuk);
	/*List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);*/
	void updatePegawai(PegawaiModel pegawai);
}
