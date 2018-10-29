package com.apap.tugas1.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

@Entity
@Table(name="jabatan_pegawai")
public class JabatanPegawaiModel implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "id_pegawai",referencedColumnName = "id", nullable = false)
	private BigInteger idPegawai;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "id_jabatan",referencedColumnName = "id", nullable = false)
	private BigInteger idJabatan;

	public BigInteger getIdPegawai() {
		return idPegawai;
	}

	public void setIdPegawai(BigInteger idPegawai) {
		this.idPegawai = idPegawai;
	}

	public BigInteger getIdJabatan() {
		return idJabatan;
	}

	public void setIdJabatan(BigInteger idJabatan) {
		this.idJabatan = idJabatan;
	}

	public BigInteger getId() {
		return id;
	}*/

	public void setId(Long id) {
		this.id = id;
	}
}
