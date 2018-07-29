package com.cg.WalletApplication.dao;

import java.util.TreeMap;

import com.cg.WalletApplication.bean.Customer;

public interface IWalletDao {

	TreeMap<String, Customer> getDetails();

	String addCustomer(Customer customer);

}
