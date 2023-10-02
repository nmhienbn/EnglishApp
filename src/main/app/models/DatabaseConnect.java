package models;

import java.io.*;
public class DatabaseConnect {

    public static void main(String argv[]) {
        try {
            String line;
            Process p = Runtime.getRuntime().exec
                    ("psql -U username -d dbname -h serverhost -f scripfile.sql");
            BufferedReader input =
                    new BufferedReader
                            (new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
}