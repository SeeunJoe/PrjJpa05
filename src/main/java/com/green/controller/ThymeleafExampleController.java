package com.green.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.green.entity.Person;

@Controller
public class ThymeleafExampleController {
	
	@GetMapping("/thymeleaf/example")
	public ModelAndView thymeleafExample() {
		ModelAndView mv = new ModelAndView();
		
		Person person = new Person();
		person.setId(1L);
		person.setName("손흥민");
		person.setAge(33);
		person.setHobbies(List.of("축구","수영","운전"));
		
		mv.addObject("person",person);
		mv.addObject("today", LocalDate.now());
		
		mv.setViewName("example");
		
		return mv;
	}
}
