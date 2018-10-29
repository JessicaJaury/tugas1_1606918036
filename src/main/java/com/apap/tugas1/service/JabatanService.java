package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	Optional<JabatanModel> findJabatanById(Long id);
	void addJabatan(JabatanModel jabatan);
	void deleteJabatan(JabatanModel jabatan);
	List<JabatanModel> viewAll();
}
