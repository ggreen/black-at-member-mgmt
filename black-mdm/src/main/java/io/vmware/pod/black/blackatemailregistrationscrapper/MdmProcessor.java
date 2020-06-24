package io.vmware.pod.black.blackatemailregistrationscrapper;

import lombok.Getter;
import nyla.solutions.core.io.csv.CsvReader;
import nyla.solutions.core.io.csv.CsvWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Gregory Green
 */
@Component
public class MdmProcessor
        implements CommandLineRunner
{

    private final CsvWriter csvWriter;
    @Getter
    private final Set<String> masterEmails;

    private final CsvReader masterCsvReader;
    private final int emailDlEmailPosition = 1;

    @Value("${emailPosition:4}")
    private int emailPosition = 4;
    private int emailDlNamePosition = 0;

    public MdmProcessor(File email_dl, CsvWriter csvWriter)
    throws IOException
    {
        this.csvWriter = csvWriter;
        masterEmails = new HashSet<String>();

        masterCsvReader = new CsvReader(email_dl);
        for (List<String> lines : masterCsvReader)
        {
            masterEmails.add(lines.get(emailDlEmailPosition));
        }
    }

    public void process(File onboarding)
    throws IOException
    {
        CsvReader reader = new CsvReader(onboarding);

        String email;
        final Set<String> onboardEmails = new HashSet<>(reader.size());

        for (int i = 0; i < reader.size(); i++)
        {
            List<String> row = reader.row(i);
            email = row.get(emailPosition).toLowerCase();
            onboardEmails.add(email);

            if (masterEmails.contains(email))
                row.set(0, "Y");
            else
                row.set(0, "N");

            csvWriter.appendRow(row);
        }

        //loop master

        masterCsvReader.stream()
                .filter(line -> !onboardEmails
                         .contains(line.get(emailDlEmailPosition)))
                .forEach( line ->
                {
                    try
                    {
                        //Added (Y/N/D)?
                        // ID
                        // Start time
                        // Completion time
                        // Email
                        // Name
                        // Enter Your Full Name
                        // Enter Your location (City and State)
                        // Enter Your Email
                        // Select your demographic:
                        // Tell us why Diversity & Inclusion is important to you.
                        String yN = "Y";
                        String id = "-1";
                        String time = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
                        String location = "NA";
                        String demographic = "NA";
                        String why = "NA";
                        String []  masterLine = {yN,id,time,
                                time,
                                line.get(emailDlEmailPosition),
                                line.get(emailDlNamePosition),
                                line.get(emailDlNamePosition),
                        location,
                                line.get(emailDlEmailPosition),
                                demographic,
                        why};

                        csvWriter.appendRow(masterLine);
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                });


    }

    @Override
    public void run(String... args)
    throws Exception
    {
        if (args == null || args.length == 0)
            throw new IllegalArgumentException("Expected onboarding document");

        process(Paths.get(args[0]).toFile());
    }
}
