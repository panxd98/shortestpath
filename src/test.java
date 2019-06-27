import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class test {
    public static  void main(String args[]){
        try {
            FileReader reader = new FileReader("src\\city.txt");
            BufferedReader br = new BufferedReader(reader);
        }catch (IOException e){
            System.out.println("error");
        }
    }
}
