# Coding Challenge By Niraj Sonawane

## Key Components of Application 

## 1:Controllers
StatisticsController & TransactionController are Responsible for accepting Transactions & Statistics Request  

## 2: ExceptionHandler
N13ExceptionHandler is responsible for handling all exceptions of application and sending appropriate response back to client

## 2: Validation
Spring-Hibernate Validation framework is used for basic validation.Custom validators are available under com.n26.validator

## 3: Calculations 
All Calculations are performed by custom TransactionSummaryCollector.  TransactionSummaryCollector provides similar output as Java 8 IntSummaryStatistics.

## 4:Cache jsr107 : 
ehcache  is used as Cache provider. Cache is setup with CreatedExpiryPolicy for 60 seconds. Cache Configuration are available in ehcache.xml & CacheSetup class.
All Read are done on cache & as Cache is setup with CreatedExpiryPolicy. Values in cache will automatically clear in 60 seconds from the time they have added in cache.
Note: Cache done not consider the timestamp of transaction.   Transaction service has additional for time stamp.  
All annotations are from jsr107     