package org.spine.iquestionapi.util;

import com.opencsv.CSVWriter;
import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.model.Question;
import org.spine.iquestionapi.model.Segment;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The utility class for CSV files
 */
public class CsvUtil {
    /**
     * Write a list of entries to a csv file
     * @param entries the entries
     * @param id the id of the question
     * @return the file
     * @throws Exception if the file cannot be written
     */
    public String entryToCsv(ArrayList<Entry> entries, long id) throws Exception {

        try{
            StringWriter stringWriter = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(stringWriter);
            List<String[]> data = new ArrayList<String[]>();

            ArrayList<String> header = new ArrayList<>(
                    Arrays.asList("id", "timestamp"));

            for (Segment segment:  entries.get(0).getQuestionnaire().getSegments()){
                List<Question> vragen = segment.getQuestions();
                for (Question question: vragen){
                    Long vraagId = question.getId();
                    header.add(vraagId.toString());
                }
            }
            String[] headerArray = header.toArray(new String[0]);
            data.add(headerArray);


            for (Entry entry: entries){
                ArrayList<String> entryData = new ArrayList<>();
                Long entryId = entry.getId();
                entryData.add(entryId.toString());

                Long entryTimeStamp = entry.getTimestamp();
                ZoneOffset zoneOffset = ZoneOffset.of("+00:00");
                LocalDateTime entryDate = LocalDateTime.ofEpochSecond(entryTimeStamp/ 1000, 0, zoneOffset);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = entryDate.format(formatter);
                entryData.add(formattedDateTime);


                for (Segment segment: entry.getQuestionnaire().getSegments()){
                    for(Question question: segment.getQuestions()){
                        entryData.add(question.getLabel());
                    }
                }
                data.add(entryData.toArray(new String[0]));
            }

            csvWriter.writeAll(data);
            csvWriter.close();
            return stringWriter.toString();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}