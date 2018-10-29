package com.apap.tugas1.service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb pegawaiDb;

	@Override
	public PegawaiModel getPegawaiByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawai.setNip(this.setNIP(pegawai));
		pegawaiDb.save(pegawai);
		/*InstansiModel instansi = pegawai.getInstansi();
		  Date tanggalLahir = pegawai.getTanggalLahir();
		  String tahunMasuk = pegawai.getTahunMasuk();
		  int urutanPegawai = 1;
		  
		  List<PegawaiModel> listSementaraNIP = this.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
		  if (!listSementaraNIP.isEmpty()) {
		   urutanPegawai = (int) (Long.parseLong(listSementaraNIP.get(listSementaraNIP.size()-1).getNip())%100) + 1;
		  }
		  
		  String kodeInstansi = Long.toString(instansi.getId());
		  
		  String pattern = "dd-MM-yy";
		  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		  
		  String tanggalLahirString = simpleDateFormat.format(tanggalLahir).replaceAll("-", "");
		  String urutanPegawaiString = urutanPegawai/10 == 0 ? ("0" + Integer.toString(urutanPegawai)) : (Integer.toString(urutanPegawai));
		  String nip = kodeInstansi + tanggalLahirString + tahunMasuk + urutanPegawaiString;
		  
		  pegawai.setNip(nip);
		  pegawaiDb.save(pegawai);*/
	}

	
	/*public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk) {
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	}*/

	@Override
	public String setNIP(PegawaiModel pegawai) {	
		String nip = "";
		nip+=pegawai.getInstansi().getId();
        
		String pattern = "ddMMyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String date = dateFormat.format(pegawai.getTanggalLahir());
        nip+=date;
        
        nip+=pegawai.getTahunMasuk();
        
        List<PegawaiModel> pegawaiSama = pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk());
        int urutanPegawai = 1;
        if(pegawaiSama.size()>0) {
        	urutanPegawai = Integer.parseInt(pegawaiSama.get(pegawaiSama.size()-1).getNip().substring(14,16))+1;
        }
        
        if(urutanPegawai<10) {
        	nip+="0";
        }
        nip+=urutanPegawai;
        
        return nip;
	}

	@Override
	public List<PegawaiModel> viewAll() {
		return pegawaiDb.findAll();
	}

	@Override
	public List<PegawaiModel> findByInstansi(InstansiModel instansi) {
		return pegawaiDb.findByInstansi(instansi);
	}

	@Override
	public Optional<PegawaiModel> getPegawaiById(Long id) {
		return pegawaiDb.findById(id);
	}

	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		PegawaiModel oldPegawai = pegawaiDb.findByNip(pegawai.getNip());
		
		if(!oldPegawai.getTanggalLahir().equals(pegawai.getTanggalLahir()) || !oldPegawai.getTahunMasuk().equals(pegawai.getTahunMasuk()) || !oldPegawai.getInstansi().equals(pegawai.getInstansi())) {
			oldPegawai.setNip(this.setNIP(pegawai));
			pegawai.setNip(oldPegawai.getNip());
		}
		oldPegawai.setNama(pegawai.getNama());
		oldPegawai.setTahunMasuk(pegawai.getTahunMasuk());
		oldPegawai.setInstansi(pegawai.getInstansi());
		oldPegawai.setTanggalLahir(pegawai.getTanggalLahir());
		oldPegawai.setTempatLahir(pegawai.getTempatLahir());
		oldPegawai.setListJabatan(pegawai.getListJabatan());
		
		pegawaiDb.save(oldPegawai);
	}

	/*@Override
	public List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir,
			String tahunMasuk) {
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	}*/
}
