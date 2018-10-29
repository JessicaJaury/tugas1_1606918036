package com.apap.tugas1.controller;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String addJabatan(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "addJabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan", jabatan);
		return "addJabatanSuccess";
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	private String delete(@ModelAttribute JabatanModel jabatan, Model model) throws Exception{
		String namaJabatan = jabatanService.findJabatanById(jabatan.getId()).get().getNama();
		try {
			jabatanService.deleteJabatan(jabatan);
	    }
		catch (Exception e) {
			model.addAttribute("message", "Maaf, jabatan tidak dapat dihapus");
			model.addAttribute("jabatan", jabatanService.findJabatanById(jabatan.getId()).get());
			return "viewJabatan";
	    }
		
		model.addAttribute("namaJabatan", namaJabatan);
		return "deleteJabatanSuccess";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String ubahJabatan(@RequestParam("idJabatan") Long id, Model model) {
		model.addAttribute("jabatan", jabatanService.findJabatanById(id).get());
		model.addAttribute("message", "");
		return "updateJabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		JabatanModel jabatanLama = jabatanService.findJabatanById(jabatan.getId()).get();
		jabatanLama.setNama(jabatan.getNama());
		jabatanLama.setDeskripsi(jabatan.getDeskripsi());
		jabatanLama.setGajiPokok(jabatan.getGajiPokok());
		jabatanService.addJabatan(jabatanLama);
		
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("message", "Data Jabatan Berhasil Diubah");
		return "updateJabatan";
	}

	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String lihatJabatan(@RequestParam("idJabatan") Long id, Model model) {
		JabatanModel jabatan = jabatanService.findJabatanById(id).get();
		List<PegawaiModel> pegawaiList = pegawaiService.viewAll();
		int counter = 0;
		for(int i=0;i<pegawaiList.size();i++) {
			for(int j=0;j<pegawaiList.get(i).getListJabatan().size();j++) {
				if(pegawaiList.get(i).getListJabatan().get(j)==jabatan)
					counter++;
			}
		}
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("jumlahPegawai", counter);
		
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		model.addAttribute("gaji", decimalFormat.format(jabatan.getGajiPokok()));
		model.addAttribute("message", "");
		return "viewJabatan";
	}
	
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	private String lihatSemuaJabatan(Model model) {
		model.addAttribute("listJabatan", jabatanService.viewAll());
		return "viewAllJabatan";
	}
}
