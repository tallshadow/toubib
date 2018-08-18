package com.adouae.toubib.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adouae.toubib.domain.dto.BiologyDTO;
import com.adouae.toubib.domain.dto.ClientDTO;
import com.adouae.toubib.domain.dto.DrugDTO;
import com.adouae.toubib.domain.dto.DrugPatientDTO;
import com.adouae.toubib.domain.dto.OrderDTO;
import com.adouae.toubib.domain.dto.PaymentDTO;
import com.adouae.toubib.domain.dto.RadiologyDTO;
import com.adouae.toubib.domain.dto.UserDTO;
import com.adouae.toubib.impl.persistence.dao.OrderDAO;
import com.adouae.toubib.impl.persistence.entity.Biology;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Doctor;
import com.adouae.toubib.impl.persistence.entity.Drug;
import com.adouae.toubib.impl.persistence.entity.DrugPatient;
import com.adouae.toubib.impl.persistence.entity.Order;
import com.adouae.toubib.impl.persistence.entity.Payment;
import com.adouae.toubib.impl.persistence.entity.Radiology;
import com.adouae.toubib.itf.domain.service.ManageOrder;

@Service("manageOrder")
@Transactional
public class ManageOrderImp implements ManageOrder {

	@Autowired
	private OrderDAO orderDAO;

	@Override
	public List<DrugDTO> getDrugs() {

		List<DrugDTO> drugDto = new ArrayList<DrugDTO>();
		for (Drug source : orderDAO.getDrugs()) {
			DrugDTO target = new DrugDTO();
			BeanUtils.copyProperties(source, target);
			drugDto.add(target);
		}
		return drugDto;
	}

	@Override
	public void removeDrug(DrugDTO d) {
		Drug draugDao = new Drug();
		BeanUtils.copyProperties(d, draugDao);
		orderDAO.removeDrug(draugDao);
	}

	@Override
	public void updateDrug(DrugDTO drugDto) {
		Drug draugDao = new Drug();
		BeanUtils.copyProperties(drugDto, draugDao);
		orderDAO.updateDrug(draugDao);
	}

	@Override
	public List<BiologyDTO> getBiologies() {

		List<BiologyDTO> biologyDto = new ArrayList<BiologyDTO>();
		for (Biology source : orderDAO.getBiologies()) {
			BiologyDTO target = new BiologyDTO();
			BeanUtils.copyProperties(source, target);
			biologyDto.add(target);
		}
		return biologyDto;
	}

	@Override
	public void updateBiologies(BiologyDTO biologyDto) {
		Biology biologyDao = new Biology();
		BeanUtils.copyProperties(biologyDto, biologyDao);
		orderDAO.updateBiologies(biologyDao);
	}

	// remove bilogy
	@Override
	public void removeBiology(BiologyDTO b) {
		Biology biologygDao = new Biology();
		BeanUtils.copyProperties(b, biologygDao);
		orderDAO.removeBiology(biologygDao);
	}

	// getters and setters
	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	@Override
	public List<RadiologyDTO> getRadiology() {
		List<RadiologyDTO> radiologyDto = new ArrayList<RadiologyDTO>();
		for (Radiology source : orderDAO.getRadiology()) {
			RadiologyDTO target = new RadiologyDTO();
			BeanUtils.copyProperties(source, target);
			radiologyDto.add(target);
		}
		return radiologyDto;
	}

	@Override
	public void updateRadiology(RadiologyDTO radiologyDto) {
		Radiology radiologyDao = new Radiology();
		BeanUtils.copyProperties(radiologyDto, radiologyDao);
		orderDAO.updateRadiology(radiologyDao);
	}

	@Override
	public void removeRadiology(RadiologyDTO r) {
		Radiology radiologygDao = new Radiology();
		BeanUtils.copyProperties(r, radiologygDao);
		orderDAO.removeRadiology(radiologygDao);
	}

	@Override
	public List<OrderDTO> getOrder(UserDTO doctor, ClientDTO client) {
		
		Client clientDao = new Client();
		Doctor doctorDao = new Doctor();
		try {
			BeanUtils.copyProperties(client, clientDao);
			BeanUtils.copyProperties(doctor, doctorDao);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<OrderDTO> orderDto = new ArrayList<OrderDTO>();

		try {

			for (Order source : orderDAO.getOrder(doctorDao, clientDao)) {
				OrderDTO target = new OrderDTO();
			
				BeanUtils.copyProperties(source, target);
				BeanUtils.copyProperties(source.getDoctor(), target.getDoctor());
				BeanUtils.copyProperties(source.getClient(), target.getClient());

				List<DrugPatient> copy = new ArrayList<DrugPatient>(source.getDrug());
				

				 for (DrugPatient d : copy) {
				 DrugPatientDTO dDto = new DrugPatientDTO();
				 BeanUtils.copyProperties(d, dDto);

				 target.getDrug().add(dDto);

				 }

				orderDto.add(target);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderDto;

	}

	@Override
	public List<Date> getOrderDate(UserDTO doctorDTO, ClientDTO clientDTO) {
		Client clientDao = new Client();
		Doctor doctorDao = new Doctor();
		try {
			BeanUtils.copyProperties(clientDTO, clientDao);
			BeanUtils.copyProperties(doctorDTO, doctorDao);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return orderDAO.getOrderDate(doctorDao, clientDao);
	}

	@Override
	public void removeOrder(int id) {
		orderDAO.removeOrder(id);
	}

	@Override
	public void updateOrder(OrderDTO orderDto) {
		Order orderDao = new Order();
		BeanUtils.copyProperties(orderDto, orderDao);
		BeanUtils.copyProperties(orderDto.getClient(), orderDao.getClient());
		BeanUtils.copyProperties(orderDto.getDoctor(), orderDao.getDoctor());

		ArrayList<DrugPatient> daoList = new ArrayList<DrugPatient>();

		for (DrugPatientDTO d : orderDto.getDrug()) {
			DrugPatient dao = new DrugPatient();
			BeanUtils.copyProperties(d, dao);
			daoList.add(dao);

		}
		orderDao.setDrug(daoList);

		orderDAO.updateOrder(orderDao);
	}

	@Override
	public void updatePayment(PaymentDTO payment) {
		Payment paymentDao = new Payment();
		BeanUtils.copyProperties(payment, paymentDao);
		BeanUtils.copyProperties(payment.getClient(), paymentDao.getClient());
		BeanUtils.copyProperties(payment.getDoctor(), paymentDao.getDoctor());

		orderDAO.updatePayment(paymentDao);

	}

	@Override
	public List<PaymentDTO> getPayments(ClientDTO selectedClient, UserDTO userDTO) {
		Client clientDao = new Client();
		try {
			BeanUtils.copyProperties(selectedClient, clientDao);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<PaymentDTO> paymentDto = new ArrayList<PaymentDTO>();
		Doctor doctor = new Doctor();
		BeanUtils.copyProperties(userDTO, doctor);
		for (Payment source : orderDAO.getPayments(clientDao, doctor)) {
			PaymentDTO target = new PaymentDTO();
			BeanUtils.copyProperties(source, target);
			paymentDto.add(target);
		}

		return paymentDto;
	}

	@Override
	public void removeFee(PaymentDTO itemToDelete) {
		Payment pDao = new Payment();

		BeanUtils.copyProperties(itemToDelete, pDao);
		orderDAO.removeFee(pDao);

	}

}
