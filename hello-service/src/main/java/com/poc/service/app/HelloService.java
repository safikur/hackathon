package com.poc.service.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.poc.circuitbreaker.AvailableBookServiceCircuitBreaker;
import com.poc.circuitbreaker.CheckedOutBookServiceCircuitBreaker;

@RestController
public class HelloService {
	
	@Autowired
	private AvailableBookServiceCircuitBreaker availableBookServiceCircuitBreaker;
	
	@Autowired
	private CheckedOutBookServiceCircuitBreaker checkedOutBookServiceCircuitBreaker;
	
	@Value("${message:Hi default}")
	private String helloMsg;
	
	@RequestMapping(value="/getbooks", method=RequestMethod.GET)
    public List<String> getBooks() {
		List<String> list = new ArrayList<>();
		for(int i=0; i<100; i++){
			list.addAll(availableBookServiceCircuitBreaker.getAvailableBooks());
		}
//		list.addAll(availableBookServiceCircuitBreaker.getAvailableBooks());
		list.addAll(checkedOutBookServiceCircuitBreaker.getCheckedOutBooks());
		
        return list;
    }
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String sayHello(){
		return helloMsg;
	}
}
