package com.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AccountDao;
import com.dao.TransactionDao;
import com.entity.AccountEntity;
import com.entity.TransactionEntity;
import com.model.AccountModel;
import com.model.TransactionModel;

@Service
public class TransactionService {

	@Autowired
	TransactionDao tDao;

	@Autowired
	AccountDao aDao;
	@Autowired
	AccountService aService;

	public TransactionModel createTransaction(TransactionModel transModel) {
		transModel.setStatus(false);

		AccountEntity accEntityFrom = new AccountEntity();
		AccountEntity accEntityTo = new AccountEntity();

		accEntityFrom.setAccNo(transModel.getFromAcc());
		accEntityTo.setAccNo(transModel.getToAcc());

		try {

			accEntityFrom = aDao.readAccount(accEntityFrom);
			accEntityTo = aDao.readAccount(accEntityTo);

			if (accEntityFrom == null) {
				// this tells that whether fromAcc exists or not
				System.out.println("No Account exists for this AccountNo.");
				
			} else if (accEntityTo == null) {
				// this tells that whether fromAcc exists or not
				System.out.println("Reciever's Account doesn't exists for this AccountNo.");
			}
			long fromAccBal = accEntityFrom.getAmount();
			long toAccBal = accEntityTo.getAmount();
			System.out.println(transModel.getAmount());
			if (fromAccBal <= Integer.parseInt(transModel.getAmount())) {
				// check for sufficient Account Balance
				System.out.println("Insufficient Balance.");
			} else {
				TransactionEntity transEntity = new TransactionEntity();
				BeanUtils.copyProperties(transModel, transEntity);
				transEntity.setFromAcc(accEntityFrom);

				try {
					transEntity = tDao.createTransaction(transEntity);
					fromAccBal = fromAccBal-Integer.parseInt(transModel.getAmount());
					toAccBal = accEntityTo.getAmount() + Integer.parseInt(transModel.getAmount());
					
					// Deduce amount from fromAcc
					accEntityFrom.setAmount(fromAccBal);
					AccountModel accModelFrom= new AccountModel();
					BeanUtils.copyProperties(accEntityFrom,accModelFrom);
					aService.updateAccount(accModelFrom);
					
				
					// Add amount to toAcc
					accEntityTo.setAmount(toAccBal);
					AccountModel accModelTo= new AccountModel();
					BeanUtils.copyProperties(accEntityTo,accModelTo);
					aService.updateAccount(accModelTo);
					
					
					BeanUtils.copyProperties(transEntity, transModel);
					transModel.setStatus(true);
					
				} catch (Exception e) {
					System.out.println("Error occured while creating transaction...");
				}
			}
		} catch (Exception e) {
			System.out.println("Error gathering Transaction information... " + e);
		}
		return transModel;

	}

	public TransactionModel viewTransaction(TransactionModel transModel) {
		transModel.setStatus(false);

		TransactionEntity transEntity = new TransactionEntity();
		BeanUtils.copyProperties(transModel, transEntity);
		System.out.println(transEntity.getTransId());
		System.out.println(transModel.getTransId());

		try {
			transEntity = tDao.readTransaction(transEntity);
			BeanUtils.copyProperties(transEntity, transModel);
			transModel.setFromAcc(transEntity.getFromAcc().getAccNo());
			transModel.setStatus(true);
		} catch (Exception e) {
			System.out.println("Error gathering Transaction information...");
		}

		return transModel;

	}

	
}
