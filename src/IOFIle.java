import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class IOFIle {
    public static void main(String args[]) {

        try {
            Scanner input= new Scanner(System.in);

            System.out.printf("Enter path of file source data");
            FileReader fileReader= new FileReader(input.nextLine());
            BufferedReader bufferedReader= new BufferedReader(fileReader);

            System.out.println("Enter path of targetFile");
            FileWriter fileWriter= new FileWriter(input.nextLine());
            BufferedWriter bufferedWriter= new BufferedWriter(fileWriter);


            String line=null;
            while ((line=bufferedReader.readLine())!= null){
                bufferedWriter.write(line+"\r\n");

            }
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
