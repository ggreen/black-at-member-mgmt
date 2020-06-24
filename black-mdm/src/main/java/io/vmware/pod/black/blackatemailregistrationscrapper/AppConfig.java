package io.vmware.pod.black.blackatemailregistrationscrapper;

import nyla.solutions.core.io.csv.CsvWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author Gregory Green
 */
@Configuration
public class AppConfig
{
    @Bean
    File email_dl_csv(@Value("${emailDistributionCsv}")
                          String emailDistributionCsv)
    {
        return Paths.get(emailDistributionCsv).toFile();
    }

    @Bean
    CsvWriter csvWriter(@Value("${outputCsvFile}")
                                String outputCsvFile)
    {
        File file =  Paths.get(outputCsvFile)
                .toFile();
        file.delete();

        return new CsvWriter(
                file
        );
    }
}
