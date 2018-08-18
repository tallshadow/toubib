package com.adouae.toubib.impl.persistence.dao;

import java.util.List;

import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Doctor;
import com.adouae.toubib.impl.persistence.entity.Folder;

public interface FolderDAO {

	public List<String> getAllergys();

	public List<String> getVaccines();

	public void updateFolder(Folder folderDao);

	public Folder getFolder(Client clientDao, Doctor doctorDao);

}
