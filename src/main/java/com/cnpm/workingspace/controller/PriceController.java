package com.cnpm.workingspace.controller;

import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.model.Price;
import com.cnpm.workingspace.security.response.ErrorResponse;
import com.cnpm.workingspace.service.PriceService;
import com.cnpm.workingspace.utils.PriceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/price")
public class PriceController {

	@Autowired
	private PriceService priceService;
	
	@GetMapping(value = "")
	public ResponseEntity<?> getAllPrice(){
        List<Price> prices = priceService.getAll();
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, prices),HttpStatus.OK);
    }
	
	@PostMapping(value = "")
	public ResponseEntity<?> insertPrice(@RequestBody Price price){
		priceService.insertPrice(price);
		return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
	}

	@PutMapping(value = "/{priceId}")
	public ResponseEntity<?> updatePrice(@PathVariable int priceId, @RequestBody Price price){
		boolean status = priceService.updatePrice(price, priceId);
		if(status){
				return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
		} else{
				return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
		}
	}

	@DeleteMapping(value = "/{priceId}")
	public ResponseEntity<?> deletePrice(@PathVariable int priceId){
		priceService.deletePrice(priceId);
		return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, null), HttpStatus.OK);
	}

	@GetMapping("/{priceId}")
	public ResponseEntity<?> getPriceById(@PathVariable int priceId){
		Optional<Price> price = priceService.getPriceById(priceId);
		if(price.isPresent()) {
			Price priceGet = price.get();
			return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, priceGet), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ErrorResponse(ErrorCode.NOT_FOUND, null), HttpStatus.OK);
		}
	}

	@GetMapping("/getByOrder")
	@ResponseBody
	public ResponseEntity<ErrorResponse> getPriceOrder(@RequestParam String nameCol, @RequestParam String sort){
		List<Price> prices = priceService.getPriceOrder(nameCol, sort);
		return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS, prices), HttpStatus.OK);
	}

	@GetMapping("/calcPrice/{id}")
	public ResponseEntity<?> calcPrice(@PathVariable int id,@RequestParam String startDate,@RequestParam String endDate){
		try{
			double cost= PriceUtils.calcPrice(id,startDate,endDate,priceService);
			return new ResponseEntity<>(new ErrorResponse(ErrorCode.SUCCESS,cost),HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<>(new ErrorResponse(ErrorCode.ERROR,e.getMessage()),HttpStatus.OK);
		}
	}
}
