package com.myapp.example;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.example.repositories.MyDataRepository;

@Controller
public class HelloController {

	@Autowired
	MyDataRepository repository;

	/**
	 * indexメソッド
	 * 
	 * @param mydata
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(@ModelAttribute("formModel") MyData mydata, ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg", "This is sample content");
		mav.addObject("formModel", mydata);
		Iterable<MyData> list = repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	/**
	 * formメソッド
	 * 
	 * @param myData
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView form(
		@ModelAttribute("formModel") @Validated MyData myData,
		BindingResult result,
		ModelAndView mav) {
		ModelAndView res = null;
		if (!result.hasErrors()) {
			repository.saveAndFlush(myData);
			res = new ModelAndView("redirect:/");
		} else {
			mav.setViewName("index");
			mav.addObject("msg", "sorry, error is occured...");
			Iterable<MyData> list = repository.findAll();
			mav.addObject("datalist", list);
			res = mav;
		}
		return res;
	}
	
	/**
	 * 
	 * @param mydata
	 * @param id
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView edit(
			@ModelAttribute MyData mydata,
			@PathVariable int id,
			ModelAndView mav) {
		mav.setViewName("edit");
		mav.addObject("title", "edit mydata.");
		Optional<MyData> data = repository.findById((long)id);
		mav.addObject("formModel", data.get());
		return mav;
	}
	
	/**
	 * 
	 * @param mydata
	 * @param mav
	 * @return
	 */
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView update(
			@ModelAttribute MyData mydata,
			ModelAndView mav) {
		repository.saveAndFlush(mydata);
		return new ModelAndView("redirect:/");
	}
	
	
	@PostConstruct
	public void init() {
		MyData d1 = new MyData();
		d1.setName("Amazon");
		d1.setAge(30);
		d1.setMail("amazon@sample.com");
		d1.setMemo("This is amazon data!");
		repository.saveAndFlush(d1);
		
		MyData d2 = new MyData();
		d2.setName("Apple");
		d2.setAge(30);
		d2.setMail("apple@sample.com");
		d2.setMemo("This is apple data!");
		repository.saveAndFlush(d2);
		
		MyData d3 = new MyData();
		d3.setName("Facebook");
		d3.setAge(30);
		d3.setMail("facebook@sample.com");
		d3.setMemo("This is facebook data!");
		repository.saveAndFlush(d3);
		
		MyData d4 = new MyData();
		d4.setName("Google");
		d4.setAge(40);
		d4.setMail("google@sample.com");
		d4.setMemo("This is google data!");
		repository.saveAndFlush(d4);
	}
	
}
