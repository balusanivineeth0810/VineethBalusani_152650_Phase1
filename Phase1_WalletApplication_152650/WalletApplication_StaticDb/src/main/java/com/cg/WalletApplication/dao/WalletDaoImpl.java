package com.cg.WalletApplication.dao;

import java.util.TreeMap;

import com.cg.WalletApplication.bean.Customer;

public class WalletDaoImpl implements IWalletDao{
public static TreeMap<String,Customer> customerDetails=null; 
static{
	customerDetails=new TreeMap<String, Customer>();
	
}
public TreeMap<String, Customer> getDetails() {
	
	return customerDetails;
}
public String addCustomer(Customer customer) {
	customerDetails.put(customer.getMobileNumber(), customer);
	return customer.getMobileNumber();
}
}
