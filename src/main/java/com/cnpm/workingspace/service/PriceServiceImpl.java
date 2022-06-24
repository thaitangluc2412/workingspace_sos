package com.cnpm.workingspace.service;

import java.util.List;
import java.util.Optional;

import com.cnpm.workingspace.dao.PriceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnpm.workingspace.model.Price;
import com.cnpm.workingspace.repository.PriceRepository;

@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
    private PriceRepository priceRepository;

	@Override
	public List<Price> getAll() {
		return priceRepository.findAll();
	}

	@Override
	public void insertPrice(Price price) {
		priceRepository.save(price);
	}

	private PriceDao priceDao;

	@Autowired
	PriceServiceImpl(PriceDao priceDao){
		this.priceDao = priceDao;
	}

	@Override
	public boolean updatePrice(Price price, int id) {
		Optional<Price> curPrice = getPriceById(id);
		System.out.println("price service");
		if (curPrice.isPresent()) {
			System.out.println("id: " + id);
			Price curPriceNew = curPrice.get();
			curPriceNew.setHourPrice(price.getHourPrice());
            curPriceNew.setDayPrice(price.getDayPrice());
            curPriceNew.setWeekPrice(price.getWeekPrice());
            curPriceNew.setMonthPrice(price.getMonthPrice());
            priceRepository.save(curPriceNew);
            return true;
		}
		return false;
	}

	@Override
	public void deletePrice(int id) {
		priceRepository.deleteById(id);
	}
	
	@Override
	public Optional<Price> getPriceById(int id) {
		return priceRepository.findById(id);
	}

	@Override
	public List<Price> getPriceOrder(String nameCol, String sort) {
		return priceDao.getPriceOrder(nameCol, sort);
	}
}
