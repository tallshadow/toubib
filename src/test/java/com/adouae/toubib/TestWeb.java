package com.adouae.toubib;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext-model.xml"})
public class TestWeb {


	    @Test
	    public void test_ml_always_return_true() {

	        System.out.println("ok");

	    }
	
}
