package io.vmware.pod.black.blackatemailregistrationscrapper;

import nyla.solutions.core.io.IO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Paths;

@SpringBootTest(
		properties = {
				"csvFile=runtime/test-report.csv"
		},
		args = {
		"runtime/emails"
})
class BlackAtEmailRegistrationScrapperApplicationTests {

	@BeforeAll
	public static void setUp()
	throws IOException
	{
		IO.delete(Paths.get("runtime/test-report.csv").toFile());
	}
	@Test
	void contextLoads() {
	}

}
