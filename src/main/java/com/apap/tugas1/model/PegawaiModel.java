package com.apap.tugas1.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="pegawai")
public class PegawaiModel implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "NIP", nullable = false, unique = true)
	private String nip;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;

	@NotNull
	@Size(max = 255)
	@Column(name = "tempat_lahir", nullable = false)
	private String tempatLahir;
	
	@NotNull
	@Column(name = "tanggal_lahir", nullable = false)
	private Date tanggalLahir;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tahun_masuk", nullable = false)
	private String tahunMasuk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_instansi",referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private InstansiModel instansi;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "jabatan_pegawai",  joinColumns = { @JoinColumn(name = "id_pegawai") },
            inverseJoinColumns = { @JoinColumn(name = "id_jabatan") })
	private List<JabatanModel> listJabatan;

	public List<JabatanModel> getListJabatan() {
		return listJabatan;
	}

	public void setListJabatan(List<JabatanModel> listJabatan) {
		this.listJabatan = listJabatan;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTempatLahir() {
		return tempatLahir;
	}

	public void setTempatLahir(String tempat_lahir) {
		this.tempatLahir = tempat_lahir;
	}

	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggal_lahir) {
		this.tanggalLahir = tanggal_lahir;
	}

	public String getTahunMasuk() {
		return tahunMasuk;
	}

	public void setTahunMasuk(String tahun_masuk) {
		this.tahunMasuk = tahun_masuk;
	}

	public InstansiModel getInstansi() {
		return instansi;
	}

	public void setInstansi(InstansiModel instansi) {
		this.instansi = instansi;
	}
	
	public int getUmur() {
		LocalDate now = LocalDate.now();
		
		LocalDate birthday = tanggalLahir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return now.getYear()-birthday.getYear();
		
	}
	
	public double getGaji() {
		double totalGaji=0.0;
		for(int i=0;i<listJabatan.size();i++) {
			if(listJabatan.get(i).getGajiPokok()>totalGaji) {
				totalGaji = listJabatan.get(i).getGajiPokok();
			}
		}
		
		double tunjangan = (instansi.getProvinsi().getPresentaseTunjangan()/100)*totalGaji;
		totalGaji+=tunjangan;
		return totalGaji;
	}
}
