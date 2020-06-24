package io.vmware.pod.black.blackatemailregistrationscrapper;

import nyla.solutions.core.io.csv.CsvWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

/**
 * @author Gregory Green
 */
@Configuration
public class AppConfig
{
    @Bean
    CsvWriter csvWriter(@Value("${csvFile}")
                                String csvFile)
    {
        return new CsvWriter(
                Paths.get(csvFile)
                .toFile()
        );
    }
}
