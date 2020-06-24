package io.vmware.pod.black.blackatemailregistrationscrapper;

import io.vmware.pod.black.blackatemailregistrationscrapper.domain.BlackAtMember;
import nyla.solutions.core.io.csv.CsvWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class EmailScrapperProcessorTest
{
    private CsvWriter csvWriter;
    private EmailScrapperProcessor subject = new EmailScrapperProcessor(csvWriter);

    @BeforeEach
    public void setUp()
    {
        csvWriter = mock(CsvWriter.class);
        subject = new EmailScrapperProcessor(csvWriter);
    }

    @Test
    void run()
    throws Exception
    {


        String inputPut = "runtime/emails";
        subject.run(inputPut);

        verify(csvWriter, atLeastOnce()).appendRow(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
        );
    }


    @Test
    public void test_duplicates()
    throws Exception
    {

        String inputPut = "runtime/duplicates";
        subject.run(inputPut);

        verify(csvWriter, timeout(1)).appendRow(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
        );

    }

    @Test
    public void test_builder()
    throws IOException
    {
        File expected = Paths.get("runtime/emails/allies support.eml").toFile();

        BlackAtMember member = subject.buildMember(expected);

        assertNotNull(member);

        assertEquals("Brent McCoubrey", member.getName());
        assertEquals("brentm@vmware.com", member.getEmail());
    }
}