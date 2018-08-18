package com.adouae.toubib.itf.domain.service;

import java.util.Date;
import java.util.List;

import com.adouae.toubib.domain.dto.BiologyDTO;
import com.adouae.toubib.domain.dto.ClientDTO;
import com.adouae.toubib.domain.dto.DrugDTO;
import com.adouae.toubib.domain.dto.OrderDTO;
import com.adouae.toubib.domain.dto.PaymentDTO;
import com.adouae.toubib.domain.dto.RadiologyDTO;
import com.adouae.toubib.domain.dto.UserDTO;

public interface ManageOrder {

	List<DrugDTO> getDrugs();

	void removeDrug(DrugDTO d);

	void updateDrug(DrugDTO drugDto);

	List<BiologyDTO> getBiologies();

	void updateBiologies(BiologyDTO biologyDto);

	void removeBiology(BiologyDTO b);

	List<RadiologyDTO> getRadiology();

	void updateRadiology(RadiologyDTO radiologyDto);

	void removeRadiology(RadiologyDTO d);

	List<OrderDTO> getOrder(UserDTO userDTO, ClientDTO selectedClient);

	List<Date> getOrderDate(UserDTO userDTO, ClientDTO selectedClient);

	void removeOrder(int id);

	void updateOrder(OrderDTO orderDto);

	void updatePayment(PaymentDTO payment);

	List<PaymentDTO> getPayments(ClientDTO selectedClient, UserDTO userDTO);

	void removeFee(PaymentDTO itemToDelete);

}
