package org.spine.iquestionapi.util;

import com.opencsv.CSVWriter;
import org.apache.logging.log4j.Logger;
import org.spine.iquestionapi.model.Entry;
import org.spine.iquestionapi.model.Question;
import org.spine.iquestionapi.model.Segment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;




public class CsvUtil {
    private static final Logger log = getLogger(CsvUtil.class);


    public String entryToCsv(ArrayList<Entry> entries, long id) throws Exception {

        String csvString = null;
        try{
            StringWriter stringWriter = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(stringWriter);
            List<String[]> data = new ArrayList<String[]>();

            ArrayList<String> header = new ArrayList<>(
                    Arrays.asList("id", "caregiver", "timestamp"));;

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
                entryData.add(entry.getCaregiver().getName());
                Long entryTimeStamp = entry.getTimestamp();
                entryData.add(entryTimeStamp.toString());
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
        catch (IOException e){
            throw new Exception(e.getMessage());
        }
    }
}