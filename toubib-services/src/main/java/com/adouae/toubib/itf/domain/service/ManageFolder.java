package com.adouae.toubib.itf.domain.service;

import java.util.List;

import com.adouae.toubib.domain.dto.ClientDTO;
import com.adouae.toubib.domain.dto.FolderDTO;
import com.adouae.toubib.domain.dto.UserDTO;

public interface ManageFolder {

	public List<String> getallergys();

	public List<String> getVaccines();

	public void updateFolder(FolderDTO folderDto);

	public FolderDTO getFolder(ClientDTO c, UserDTO currentUser);

	

}
