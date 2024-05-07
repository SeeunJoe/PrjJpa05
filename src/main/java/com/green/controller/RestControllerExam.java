package com.green.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerExam {
	// Get
	@GetMapping("/RestExam")	
	public  String  getRestExam() {
		return "Get data";
	}
	// Post
	@PostMapping("/RestExam")
	public  String  postRestExam() {
		return "Post data";
	}
	// Delete
	@DeleteMapping("/RestExam")
	public  String  deleteRestExam() {
		return "delete data";
	}
	// Patch
	@PatchMapping("/RestExam")
	public  String  patchRestExam() {
		return "patch data";
	}
	// Put
	@PutMapping("/RestExam")
	public  String  putRestExam() {
		return "Put data";
	}
	
}





