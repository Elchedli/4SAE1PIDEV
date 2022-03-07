package com.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GraphController {

	@GetMapping("/displayBarGraph")
	private String barGraph(Model model) {
		Map<String, Integer> surveyMap = new LinkedHashMap<>();
		surveyMap.put("France",40);
		surveyMap.put("Tunisia", 60);
		surveyMap.put("Algeria", 30);
		surveyMap.put("UK", 20);
		model.addAttribute("",surveyMap);
		return"barGraph ";

	}
	
	@GetMapping("/displayPieChart")
	private String pieChart() {
		return"";

	}
}
