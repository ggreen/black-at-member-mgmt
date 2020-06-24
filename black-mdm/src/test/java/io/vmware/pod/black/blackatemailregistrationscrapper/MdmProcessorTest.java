package io.vmware.pod.black.blackatemailregistrationscrapper;

import nyla.solutions.core.io.IO;
import nyla.solutions.core.io.csv.CsvWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MdmProcessorTest
{
    private MdmProcessor mdmProcessor;
    private File email_dl = Paths.get("runtime/db/6-22-2020/email_distribution.csv").toFile();
    private File onboarding = Paths.get("runtime/db/6-22-2020/onboarding.csv").toFile();
    private CsvWriter csvWriter;

    @BeforeEach
    public void setUp()
    throws IOException
    {
         csvWriter = mock(CsvWriter.class);

        mdmProcessor = new MdmProcessor(email_dl, csvWriter);
    }

    @Test
    public void test_run()
    {
        assertThrows(IllegalArgumentException.class, () -> mdmProcessor.run());

    }

    @Test
    void test_all_records_in_database()
    throws IOException
    {

        String threeMdmsCSv = "\"Aamandeep Singh\",\"aamandeeps@vmware.com\"\n" +
                "\"Aamir Siddiqi\",\"aamirs@vmware.com\"\n" +
                "\"Aaron Blackmon\",\"ablackmon@vmware.com\"";

        File three_email_dl = Paths.get("target/3_mdm.csv").toFile();
        IO.writeFile(three_email_dl, threeMdmsCSv);

        String onboarding = "N,28,1/6/20 17:48:14,1/6/20 17:52:25,ablackmon@vmware.com,aamirs M. Graves III,aamirs Graves,94114,ablackmon@vmware.com,\"Black (African, African American)\",I am joining to see how I can support increasing diversity in my workplace.";

        File one_onboarding = Paths.get("target/2_onboarding.csv").toFile();
        IO.writeFile(one_onboarding, onboarding);

        CsvWriter mockWriterForTest = mock(CsvWriter.class);

        MdmProcessor allProcessor = new MdmProcessor(three_email_dl, mockWriterForTest);
        allProcessor.process(one_onboarding);

        int expectedTimes = 2;
        verify(mockWriterForTest, times(1)).appendRow(anyList());
        verify(mockWriterForTest, times(expectedTimes)).appendRow(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString()
        );
    }

    @Test
    public void test_process()
    throws IOException
    {
        mdmProcessor.process(onboarding);
        verify(csvWriter, atLeastOnce()).appendRow(any(List.class));

    }
}