package com.josedavid.misfinanzas.otros.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class SaveObjectToJson {

    public boolean saveObjectToJson(File filesDir,File folder, String fileName, Object objectToSave){
        boolean success = false;

        folder.mkdirs();
        File file = new File(folder.getAbsolutePath() +fileName + ".json");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file,objectToSave);
            success = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return success;

    }

}
