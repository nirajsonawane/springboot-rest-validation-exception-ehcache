package com.niraj.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niraj.entites.Statistic;
import com.niraj.service.StatisticsService;
/**
 * 
 * @author Niraj Sonawane
 * 
 *	Rest Controller responsible for accepting statistics get request 
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

	Logger logger = LoggerFactory.getLogger(StatisticsController.class);

	@Autowired
	private StatisticsService statistics;

	@GetMapping
	public Statistic getStatistics() {
		
		return statistics.getStatistics();
	}

}
