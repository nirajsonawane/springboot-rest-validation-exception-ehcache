package com.niraj.entites;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Statistic {
	
	private String sum;
	private String avg;
	private String max;
	private String min;
	private int count;

}
