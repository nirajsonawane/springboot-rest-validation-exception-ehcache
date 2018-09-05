package com.niraj.validator;

import javax.validation.GroupSequence;
/**
 * 
 * @author Niraj Sonawane
 * 
 * Interface is responsible for setting the sequence/Priority of Custom validations 
 * 
 *
 */
@GroupSequence({ ValidUtcDateConstraint.class, FutureDateConstraint.class,OlderDateConstraint.class })
public interface DateChecks {}