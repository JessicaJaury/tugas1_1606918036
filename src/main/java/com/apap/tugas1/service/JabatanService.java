package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	void addJabatan(JabatanModel jabatan);
	List<JabatanModel> viewAll();
	JabatanModel findJabatanById(BigInteger id);
	void deleteJabatan(JabatanModel jabatan);
}
