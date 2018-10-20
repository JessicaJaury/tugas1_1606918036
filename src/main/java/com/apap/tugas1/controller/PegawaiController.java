package com.apap.tugas1.controller;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
		@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("allJabatan", jabatanService.viewAll());
		model.addAttribute("allInstansi", instansiService.viewAll());
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String lihatPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);
		
		DecimalFormat df = new DecimalFormat("#");
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gaji", df.format(pegawai.getGaji()));
		model.addAttribute("message", "");
		return "viewPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String addJabatan(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setDaftarJabatan(new ArrayList<JabatanModel>());
		pegawai.getDaftarJabatan().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allProvinsi", provinsiService.viewAll());
		model.addAttribute("allInstansi", instansiService.viewAll());
		model.addAttribute("allJabatan", jabatanService.viewAll());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		model.addAttribute("tanggalLahir", dateFormat.format(date));
		return "addPegawai";
	}
	
	@RequestMapping(value = "/pegawai/instansi", method = RequestMethod.GET)
	@ResponseBody
	public List<InstansiModel> getInstansi(@RequestParam (value = "provinsiId", required = true) BigInteger provinsiId) {
	    ProvinsiModel provinsi = provinsiService.findProvinsiById(provinsiId);
		return instansiService.viewByProvinsi(provinsi);
	}
	
	@RequestMapping(value = "/pegawai/provinsi", method = RequestMethod.GET)
	@ResponseBody
	public List<ProvinsiModel> getProvinsi(@RequestParam (value = "instansiId", required = true) BigInteger instansiId) {
	    String namaInstansi = instansiService.findInstansiById(instansiId).getNama();
	    List<InstansiModel> daftarInstansi= instansiService.viewByNama(namaInstansi);
	    List<ProvinsiModel> provinsi = new ArrayList<ProvinsiModel>();
	    for(int i=0;i<daftarInstansi.size();i++) {
	    	provinsi.add(daftarInstansi.get(i).getProvinsi());
	    }
		return provinsi;
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"addRow"}, method = RequestMethod.POST)
	public String addRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		pegawai.getDaftarJabatan().add(new JabatanModel());
	    model.addAttribute("pegawai", pegawai);
	    
	    model.addAttribute("allProvinsi", provinsiService.viewAll());
		model.addAttribute("allInstansi", instansiService.viewByProvinsi(pegawai.getInstansi().getProvinsi()));
		model.addAttribute("allJabatan", jabatanService.viewAll());
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String tanggalLahir = simpleDateFormat.format(pegawai.getTanggalLahir());
		model.addAttribute("tanggalLahir", tanggalLahir);
	    return "addPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params={"deleteRow"})
	private String deleteRow(@ModelAttribute PegawaiModel pegawai, Model model,final BindingResult bindingResult, final HttpServletRequest req) {
		Integer rowId =  Integer.valueOf(req.getParameter("deleteRow"));
		pegawai.getDaftarJabatan().remove(rowId.intValue());
		model.addAttribute("pegawai", pegawai); 
		
	    model.addAttribute("allProvinsi", provinsiService.viewAll());
		model.addAttribute("allInstansi", instansiService.viewByProvinsi(pegawai.getInstansi().getProvinsi()));
		model.addAttribute("allJabatan", jabatanService.viewAll());
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String tanggalLahir = simpleDateFormat.format(pegawai.getTanggalLahir());
		model.addAttribute("tanggalLahir", tanggalLahir);
		
		return "addPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, @RequestParam("instansi") BigInteger idInstansi, Model model) {
		InstansiModel instansi = instansiService.findInstansiById(idInstansi);
		pegawai.setInstansi(instansi);
		pegawaiService.addPegawai(pegawai);
		
		model.addAttribute("pegawai", pegawai);

		return "addPegawaiSuccess";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String lihatPegawaiTermudaTertua(@RequestParam("idInstansi") BigInteger idInstansi, Model model) {
		InstansiModel instansi = instansiService.findInstansiById(idInstansi);
		List<PegawaiModel> daftarPegawai = instansi.getPegawaiList();
		

		if(daftarPegawai.isEmpty()) {
			model.addAttribute("message", "Tidak ada pegawai yang terdaftar dalam instansi ini");
			model.addAttribute("pegawaiTermuda", new PegawaiModel());
			model.addAttribute("pegawaiTertua",  new PegawaiModel());
			return "viewPegawaiTermudaTertua";
		}
		PegawaiModel pegawaiTermuda = daftarPegawai.get(0);
		PegawaiModel pegawaiTertua = daftarPegawai.get(0);
		if(daftarPegawai.size()==1) {
			model.addAttribute("message", "Hanya ada satu pegawai dalam intansi ini");
		}
		else {
			for(int i=0;i<daftarPegawai.size();i++) {
				if(daftarPegawai.get(i).getUmur()<pegawaiTermuda.getUmur()) {
					pegawaiTermuda = daftarPegawai.get(i);
				}
				else if (daftarPegawai.get(i).getUmur()>pegawaiTertua.getUmur()){
					pegawaiTertua = daftarPegawai.get(i);
				}
			}
			model.addAttribute("message", "");
		}	
		DecimalFormat df = new DecimalFormat("#");
		
		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("gajiPegawaiTermuda", df.format(pegawaiTermuda.getGaji()));
		
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		model.addAttribute("gajiPegawaiTertua", df.format(pegawaiTertua.getGaji()));
		
		return "viewPegawaiTermudaTertua";
	}
	
	@RequestMapping("/pegawai/cari")
	private String cariPegawai(Model model) {
		model.addAttribute("allPegawai", pegawaiService.viewAll());
		model.addAttribute("allProvinsi", provinsiService.viewAll());
		model.addAttribute("allInstansi", instansiService.viewAll());
		model.addAttribute("allJabatan", jabatanService.viewAll());
		return "cariPegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET, params = {"idProvinsi","idInstansi","idJabatan"})
	private String cariPegawaiProvinsiInstansiJabatan(@RequestParam("idProvinsi") BigInteger idProvinsi, @RequestParam("idInstansi") BigInteger idInstansi, @RequestParam("idJabatan") BigInteger idJabatan, Model model) {
		model.addAttribute("allProvinsi", provinsiService.viewAll());
		model.addAttribute("allInstansi", instansiService.viewAll());
		model.addAttribute("allJabatan", jabatanService.viewAll());
		if(idProvinsi.intValue()==0&&idJabatan.intValue()==0) {
			InstansiModel instansi = instansiService.findInstansiById(idInstansi);
			model.addAttribute("allPegawai", pegawaiService.findByInstansi(instansi));
		}
		else if(idProvinsi.intValue()==0&&idInstansi.intValue()==0) {
			JabatanModel jabatan = jabatanService.findJabatanById(idJabatan);
			List<PegawaiModel> allPegawai = pegawaiService.viewAll();
			List<PegawaiModel> allPegawaiInJabatan = new ArrayList<PegawaiModel>();
			for(int i=0;i<allPegawai.size();i++) {
				for(int j=0;j<allPegawai.get(i).getDaftarJabatan().size();j++) {
					if(allPegawai.get(i).getDaftarJabatan().get(j)==jabatan)
						allPegawaiInJabatan.add(allPegawai.get(i));
				}
			}
			model.addAttribute("allPegawai",allPegawaiInJabatan);
		}
		else if(idJabatan.intValue()==0&&idInstansi.intValue()==0) {
			ProvinsiModel provinsi = provinsiService.findProvinsiById(idProvinsi);
			List<InstansiModel> allInstansiInProvinsi = instansiService.viewByProvinsi(provinsi);
				
			List<PegawaiModel> allPegawai = pegawaiService.viewAll();
			List<PegawaiModel> allPegawaiInProvinsi = new ArrayList<PegawaiModel>();
			for(int i=0;i<allPegawai.size();i++) {
				if(allInstansiInProvinsi.contains(allPegawai.get(i).getInstansi())) {
					allPegawaiInProvinsi.add(allPegawai.get(i));
				}
			}
			model.addAttribute("allPegawai",allPegawaiInProvinsi);
		}
		else if(idProvinsi.intValue()==0) {
			InstansiModel instansi = instansiService.findInstansiById(idInstansi);
			List<PegawaiModel> allPegawaiInInstansi = pegawaiService.findByInstansi(instansi);
			
			JabatanModel jabatan = jabatanService.findJabatanById(idJabatan);
			List<PegawaiModel> allPegawaiInJabatanInstansi = new ArrayList<PegawaiModel>();
			for(int i=0;i<allPegawaiInInstansi.size();i++) {
				for(int j=0;j<allPegawaiInInstansi.get(i).getDaftarJabatan().size();j++) {
					if(allPegawaiInInstansi.get(i).getDaftarJabatan().get(j)==jabatan)
						allPegawaiInJabatanInstansi.add(allPegawaiInInstansi.get(i));
				}
			}
			model.addAttribute("allPegawai",allPegawaiInJabatanInstansi);
		}
		else if(idInstansi.intValue()==0) {
			ProvinsiModel provinsi = provinsiService.findProvinsiById(idProvinsi);
			List<InstansiModel> allInstansiInProvinsi = instansiService.viewByProvinsi(provinsi);
			
			JabatanModel jabatan = jabatanService.findJabatanById(idJabatan);
			List<PegawaiModel> allPegawaiInProvinsiJabatan = new ArrayList<PegawaiModel>();
			List<PegawaiModel> allPegawai = pegawaiService.viewAll();
			for(int i=0;i<allPegawai.size();i++) {
				if(allInstansiInProvinsi.contains(allPegawai.get(i).getInstansi())) {
					for(int j=0;j<allPegawai.get(i).getDaftarJabatan().size();j++) {
						if(allPegawai.get(i).getDaftarJabatan().get(j)==jabatan) {
							allPegawaiInProvinsiJabatan.add(allPegawai.get(i));
						}
					}
				}
			}
		
			model.addAttribute("allPegawai",allPegawaiInProvinsiJabatan);
		}
		else if(idJabatan.intValue()==0) {
			InstansiModel instansi = instansiService.findInstansiById(idInstansi);
			List<PegawaiModel> allPegawai = pegawaiService.findByInstansi(instansi);
			model.addAttribute("allPegawai",allPegawai);
		}
		else {
			InstansiModel instansi = instansiService.findInstansiById(idInstansi);
			List<PegawaiModel> allPegawai = pegawaiService.findByInstansi(instansi);
			
			JabatanModel jabatan = jabatanService.findJabatanById(idJabatan);
			List<PegawaiModel> allPegawaiInJabatanInstansiProvinsi = new ArrayList<PegawaiModel>();
			for(int i=0;i<allPegawai.size();i++) {
				for(int j=0;j<allPegawai.get(i).getDaftarJabatan().size();j++) {
					if(allPegawai.get(i).getDaftarJabatan().get(j)==jabatan)
						allPegawaiInJabatanInstansiProvinsi.add(allPegawai.get(i));
				}
			}
			model.addAttribute("allPegawai",allPegawaiInJabatanInstansiProvinsi);
		}
		
		return "cariPegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	private String ubahPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("message", "");

		return "changePegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawaiService.addPegawai(pegawai);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("message", "Berhasil mengubah data pegawai");

		return "addPegawaiSuccess";
	}
}
