package com.example.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.*;
import java.time.LocalDateTime;
import java.util.regex.Pattern;




@RestController
public class QueryParserController {
	

    private static final String COLUMN_NAMES_FILE = "column_names.txt";
    private static final String COLUMN_TYPES_FILE = "column_types.txt";
    private static final String TABLE_FILE = "table.txt";
   
    @Autowired
    private StatusRepository statusRepository;
    
    @PostMapping("/createTable")
    public String createTable(@RequestBody String createQuery) {
        try {
            String[] columns = parseCreateQuery(createQuery);
            FileWriter namesWriter = new FileWriter(COLUMN_NAMES_FILE);
            FileWriter typesWriter = new FileWriter(COLUMN_TYPES_FILE);
            BufferedWriter namesBufferedWriter = new BufferedWriter(namesWriter);
            BufferedWriter typesBufferedWriter = new BufferedWriter(typesWriter);

            for (String column : columns) {
                String[] parts = column.trim().split(" ");
                namesBufferedWriter.write(parts[0] + "\n");
                typesBufferedWriter.write(parts[1] + "\n");
            }

            namesBufferedWriter.close();
            typesBufferedWriter.close();

            saveOperationStatus("SUCCESS");
             
            return "Table created successfully.";
        } catch (IOException e) {
        	saveOperationStatus("FAILURE");
            return "Error creating table: " + e.getMessage();
        }
    }


    @PostMapping("/insertRow")
    public String insertRow(@RequestBody String insertQuery) throws Exception {
    	 Pattern pattern = Pattern.compile("\\((\\d+),\\s*'([^']*)'\\)");
         java.util.regex.Matcher matcher = pattern.matcher(insertQuery);

         try (BufferedWriter writer = new BufferedWriter(new FileWriter(TABLE_FILE, true))) {
             while (matcher.find()) {
                 int id = Integer.parseInt(matcher.group(1));
                 String name = matcher.group(2);
                 writer.write(id + "," + name + "\n");
             }
             saveOperationStatus("SUCCESS");
            
         } catch (IOException e) {
        	 saveOperationStatus("FAILURE");
        	 
        	 return "Error While Inserting: " + e.getMessage();
         }
		return "Records Inserted successfully";
    }

   

    private static String[] parseCreateQuery(String createQuery) {
        String columnsString = createQuery.substring(createQuery.indexOf("(") + 1, createQuery.indexOf(")"));
        return columnsString.split(",");
    }

    private void saveOperationStatus(String status) {
        OperationStatus operationStatus = new OperationStatus();
        operationStatus.setStatus(status);
        operationStatus.setTimestamp(LocalDateTime.now());
        statusRepository.save(operationStatus);
    }



}
