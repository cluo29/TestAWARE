package io.github.cluo29.contextdatareading.semantization.config;


/* https://github.com/cluo29 Chu reused their code
 * Copyright 2014 Szymon Bielak <bielakszym@gmail.com> and
 *     Michał Rus <https://michalrus.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.*;


public class JsonConfigReader {

    private static final String jsonConfigFileName = "json_configuration.json";

    public Configuration deserializeConfiguration() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonConfiguration = readJsonFileFromPath();
            return mapper.readValue(jsonConfiguration, Configuration.class);
        } catch (Exception e) {
            System.out.print("ERROR:Syntax of a json file is not valid:");
            e.printStackTrace();
        }
        return null;
    }


    private String readJsonFileFromPath() throws IOException {
        String result = "";
        try {
            Log.d("Tester", "JSON33");
            File configFile = new File("C:\\Users\\tosh\\Documents\\Ania\\studia\\5 rok\\mobilne\\context-simulator2\\context-simulator2\\src\\resources\\json_configuration.json");
            InputStream stream= new FileInputStream(configFile);
            result = IOUtils.toString(stream);
            Log.d("Tester", "JSON37");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Tester", "JSON40");
        }
        return result;
    }



}
