package com.recruiting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.recruiting.serviceImpl.ApplicationServiceImpl;

@SpringBootTest
class RecruitApplicationTests {

	 @Autowired
	 private MockMvc mockMvc;
	 
	 @Autowired
	 ApplicationServiceImpl oApplicationServiceImpl;
	 
	@Test
	void contextLoads() {
	}

}
