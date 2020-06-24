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
		},
		args = {
		"runtime/db/6-22-2020/Onboarding.csv"
})
class BlackMdmAppTest
{

	@BeforeAll
	public static void setUp()
	throws IOException
	{

	}
	@Test
	void contextLoads() {

	}

}
