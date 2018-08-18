package com.adouae.toubib.impl.persistence.dao;

import java.util.Date;
import java.util.List;

import com.adouae.toubib.impl.persistence.entity.Biology;
import com.adouae.toubib.impl.persistence.entity.Client;
import com.adouae.toubib.impl.persistence.entity.Doctor;
import com.adouae.toubib.impl.persistence.entity.Drug;
import com.adouae.toubib.impl.persistence.entity.Order;
import com.adouae.toubib.impl.persistence.entity.Payment;
import com.adouae.toubib.impl.persistence.entity.Radiology;

public interface OrderDAO {

	public List<Drug> getDrugs();

	public void removeDrug(Drug d);

	public void updateDrug(Drug draugDao);

	public List<Biology> getBiologies();

	public void updateBiologies(Biology biologyDao);

	public void removeBiology(Biology biologygDao);

	public List<Radiology> getRadiology();

	void updateRadiology(Radiology radiologyDao);

	void removeRadiology(Radiology radiologygDao);

	public List<Order> getOrder(Doctor doctorDao, Client client);

	public List<Date> getOrderDate(Doctor doctorDao, Client client);

	public void removeOrder(int id);

	public void updateOrder(Order orderDao);

	public void updatePayment(Payment paymentDao);

	public List<Payment> getPayments(Client selectedClient, Doctor doctor);

	public void removeFee(Payment pDao);

}
