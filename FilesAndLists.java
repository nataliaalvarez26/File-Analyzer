
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Paths;
import java.util.List;

public class FilesAndLists {

    public static void main(String[] args){
       // Path filePath= Paths.get("C: /Users/natalia_alvarez/Downloads", "Untiled.rtd");
    }
    private List<String> fileToString(){
        Path filePath= Paths.get("C: /Users/natalia_alvarez/Downloads");
        try{
            List<String> lines = Files.readAllLines(filePath,Charset.defaultCharset());
            return lines;
        }
        catch (IOException E){
            return null;
        }

    }

    private void writeToFile( Path location, List<String> toWrite ){
        try {
            Files.write(location,toWrite,Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
