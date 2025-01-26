package com.assessment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@SpringBootTest
class AssessmentBackendApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testMain() {
		try (var mockedSpringApplication = mockStatic(SpringApplication.class)) {
			String[] args = {};
			AssessmentBackendApplication.main(args);

			mockedSpringApplication.verify(() -> SpringApplication.run(AssessmentBackendApplication.class, args), times(1));
		}
	}
}
