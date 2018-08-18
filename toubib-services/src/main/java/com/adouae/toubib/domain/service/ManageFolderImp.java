package com.adouae.toubib.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adouae.toubib.domain.dto.ClientDTO;
import com.adouae.toubib.domain.dto.FolderDTO;
import com.adouae.toubib.domain.dto.SurgicalHistoryDTO;
import com.adouae.toubib.domain.dto.UserDTO;
import com.adouae.toubib.impl.persistence.dao.FolderDAO;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Doctor;
import com.adouae.toubib.impl.persistence.entity.Folder;
import com.adouae.toubib.impl.persistence.entity.SurgicalHistory;
import com.adouae.toubib.itf.domain.service.ManageFolder;

@Service("manageFolder")
@Transactional
public class ManageFolderImp implements ManageFolder {

	@Autowired
	private FolderDAO folderDAO;

	@Override
	public List<String> getallergys() {
		return folderDAO.getAllergys();
	}

	@Override
	public List<String> getVaccines() {
		return folderDAO.getVaccines();
	}

	@Override
	public void updateFolder(FolderDTO folderDto) {

		Folder folderDao = new Folder();
		BeanUtils.copyProperties(folderDto, folderDao);
		BeanUtils.copyProperties(folderDto.getClient(), folderDao.getClient());
		BeanUtils.copyProperties(folderDto.getDoctor(), folderDao.getDoctor());

		ArrayList<SurgicalHistory> daoList = new ArrayList<SurgicalHistory>();

		for (SurgicalHistoryDTO d : folderDto.getSurgicalHistory()) {
			SurgicalHistory dao = new SurgicalHistory();
			BeanUtils.copyProperties(d, dao);
			daoList.add(dao);

		}
		folderDao.setSurgicalHistory(daoList);

		folderDAO.updateFolder(folderDao);
	}

	// getters & setters

	public FolderDAO getFolderDAO() {
		return folderDAO;
	}

	public void setFolderDAO(FolderDAO folderDAO) {
		this.folderDAO = folderDAO;
	}

	@Override
	public FolderDTO getFolder(ClientDTO clientDto, UserDTO doctorDto) {

		Client clientDao = new Client();
		Doctor doctorDao = new Doctor();
		
		BeanUtils.copyProperties(clientDto, clientDao);
		BeanUtils.copyProperties(doctorDto, doctorDao);

		FolderDTO folderDto = new FolderDTO();
		Folder folderDao = folderDAO.getFolder(clientDao, doctorDao);

		if (folderDao != null) {
			BeanUtils.copyProperties(folderDao, folderDto);

			BeanUtils.copyProperties(folderDao.getClient(), folderDto.getClient());

			BeanUtils.copyProperties(folderDao.getDoctor(), folderDto.getDoctor());

			if (folderDao.getSurgicalHistory() != null) {
				ArrayList<SurgicalHistoryDTO> listDto = new ArrayList<SurgicalHistoryDTO>();
				for (SurgicalHistory d : folderDao.getSurgicalHistory()) {
					SurgicalHistoryDTO dto = new SurgicalHistoryDTO();

					BeanUtils.copyProperties(d, dto);

					listDto.add(dto);
				}
				folderDto.setSurgicalHistory(listDto);
				
			}
			//get addictions
			if (folderDao.getAddiction() != null) {
				for(String addiction : folderDao.getAddiction()){
					folderDto.getAddiction().add(addiction);
				}
			}
			//get MedicationHistory
			if(folderDao.getMedicationHistory() != null){
				for(String hisMed: folderDao.getMedicationHistory()){
					folderDto.getMedicationHistory().add(hisMed);
				}
			}
			
			//get vaccins
			if(folderDao.getVaccins() != null){
				for(String vaccin: folderDao.getVaccins()){
					folderDto.getVaccins().add(vaccin);
				}
			}

		}
		return folderDto;
	}

}
