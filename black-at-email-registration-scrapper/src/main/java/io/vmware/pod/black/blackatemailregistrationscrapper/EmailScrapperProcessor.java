package io.vmware.pod.black.blackatemailregistrationscrapper;

import io.vmware.pod.black.blackatemailregistrationscrapper.domain.BlackAtMember;
import nyla.solutions.core.exception.RequiredException;
import nyla.solutions.core.io.IO;
import nyla.solutions.core.io.csv.CsvWriter;
import nyla.solutions.core.util.Text;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gregory Green
 */
@Component
public class EmailScrapperProcessor implements CommandLineRunner
{
    private final CsvWriter csvWriter;
    private String pattern = "*.eml";

    public EmailScrapperProcessor(CsvWriter csvWriter)
    {
        this.csvWriter = csvWriter;
    }

    @Override
    public void run(String... args)
    throws Exception
    {
        if (args == null || args.length == 0)
            throw new RequiredException("input-directory");

        String input = args[0];

        File[] files = IO.listFiles(Paths.get(input).toFile(), pattern);
        BlackAtMember member;
        Set<String> memberEmails = new HashSet<>();
        for (File file : files)
        {
            member = buildMember(file);

            if (member == null)
                continue;

            //filter duplicates by emails
            if (memberEmails.contains(member.getEmail()))
                continue;

            memberEmails.add(member.getEmail());

            csvWriter.appendRow(
                    String.valueOf(member.getId()),
                    member.getStartTime().format(DateTimeFormatter.ISO_DATE),
                    member.getCompletion().format(DateTimeFormatter.ISO_DATE),
                    member.getEmail(),
                    member.getName(),
                    member.getName(),
                    member.getLocation(),
                    member.getEmail(),
                    member.getDemographic(),
                    member.getWay()
            );
        }
    }

    protected BlackAtMember buildMember(File file)
    throws IOException
    {
        if (!file.exists())
            return null;

        String content = IO.readFile(file);
        String name = Text.parseRE(content, "From: ", " <");
        String email = Text.parseRE(content, "From:.*<", ">");

        LocalDateTime now = LocalDateTime.now();

        return BlackAtMember.builder()
                .name(name)
                .email(email)
                .startTime(now)
                .completion(now)
                .build();
    }

}
