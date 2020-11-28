import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Filter;

public class IOFIle {
    public static void main(String args[]) throws IOException {
        Scanner input = new Scanner(System.in);
        File fileSource = null;
        File fileTarget = null;
        String path = "IOFile\\";

        while (true) {
            menu();
            System.out.println("Choice: ");
            int choice = (int) checkNumber(" choice menu!");

            switch (choice) {
                case 0:
                    System.out.println("bye bye ^^");
                    return;
                case 1:
                    fileSource = inputFile(fileSource, input, path);
//                    System.out.println(fileSource);
                    break;
                case 2:
                    copyFile(fileSource, fileTarget, input, path);
                    break;
                case 3:
                    listFile();
                    break;
                case 4:
                    pathFile();
                    break;
                case 5:
                    System.out.println("Enter name file display");
                    displayFile(input.nextLine());
                    break;
                case 6:
                    CSVReader();
                    break;
                case 7:
                    createFolder(input);
                    break;
                case 8:
                    path = changePath(input);
                    System.out.println(path);
                    break;
                case 9:
                    System.out.println(deleteFile(input,path));
                    break;
                case 10:
                    filterFile(input,path);
                    break;
                default:
                    break;
            }
        }
    }

    private static void menu() {
        System.out.println("Menu: ");
        System.out.println("1. Input Data in FIle");
        System.out.println("2. Clone newFile ");
        System.out.println("3. Display name file exit");
        System.out.println("4. Display path file exit");
        System.out.println("5. Display file");
        System.out.println("6. ReaderCSV");
        System.out.println("7. Create folder");
        System.out.println("8. Change path of file input data");
        System.out.println("9. Delete File");
        System.out.println("10. Find file in folder");
        System.out.println("0. Exit");
        System.out.println("");
    }

    private static int checkNumber(String mess) {
        boolean check = true;
        int number = 0;
        Scanner input = new Scanner(System.in);
        while (check) {
            try {
                number = input.nextInt();
                check = false;
            } catch (Exception e) {
                System.out.println("Please again " + mess);
            }
        }
        return number;
    }

    private static File inputFile(File fileSource, Scanner input, String path) throws IOException {
        System.out.print("Enter name file have save: ");
        String nameSource = input.nextLine();
        fileSource = new File(path + nameSource);
        FileWriter fileWriter = new FileWriter(fileSource, true);

        System.out.println("Enter data in line: ");
        fileWriter.write(input.nextLine() + "\r\n");
        fileWriter.close();
        return fileSource;
    }

    private static void copyFile(File fileSource, File targetFile, Scanner input, String path) throws IOException {
        boolean check = true;
        long amount = 0;
        do {
            System.out.println("Enter file name source: ");
            String pathSource = path + input.nextLine();
            fileSource = new File(pathSource);
            if (fileSource.exists()) {
                check = false;
                break;
            }
            System.out.println("Not exits file");
        } while (check);

        System.out.println("Enter file name target: ");
        targetFile = new File(path + input.nextLine());
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        FileReader fileReader = new FileReader(fileSource);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        FileWriter fileWriter = new FileWriter(targetFile, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            bufferedWriter.write(line + "\r\n");
            amount += line.length();
        }
        bufferedReader.close();
        bufferedWriter.close();
        System.out.println("Success full copy: " + amount + " char");
    }

    private static void listFile() {
        File dir = new File("IOFile");
        String[] paths = dir.list();
        System.out.println(" List file: ");
        for (String path : paths) {
            System.out.println(path);
        }
    }

    private static void pathFile() {
        File dir = new File("IOFile");
        File[] paths = dir.listFiles();
        System.out.println(" List file: ");
        for (File path : paths) {
            System.out.println(path.getAbsoluteFile());
        }
    }

    private static void displayFile(String fileName) throws IOException {
        File file = new File("IOFile\\" + fileName);
        if (!file.exists()) {
            System.out.println("Not exit file");
            return;
        }
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        bufferedReader.close();
        System.out.println("");
    }

    private static void CSVReader() {
        String csvFile = "csv/country.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] country = line.split(csvSplitBy);
                System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void createFolder(Scanner input) {
        System.out.print("name Folder: ");
        String nameFolder = input.nextLine();
        File dir = new File(nameFolder);
        if (dir.exists()) {
            System.out.println("file has exist");
            return;
        }
        dir.mkdir();
    }

    private static String changePath(Scanner input) {
        System.out.println(" Change path folder new(default = IOFile)");
        String nameDir = input.nextLine();
        return nameDir + "\\";
    }

    private static String deleteFile(Scanner input, String path){
        System.out.println(" Delete file : ");
        String nameFile=input.nextLine();
        File file= new File(path+nameFile);
        if (file.exists()){
            file.delete();
            return "Success delete file name "+nameFile;
        }
        return "Not exist file "+nameFile;
    }

    private static void filterFile (Scanner input, String path) throws IOException{
        System.out.println("File has ends with: ");
        String endCharOfFile=input.nextLine();

        File dir= new File(path);
        String[] listFile=dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(endCharOfFile)){
                    return true;
                }
                return false;
            }
        });
        if (listFile.length==0){
            System.out.println("no file with "+ endCharOfFile );
        }
        for (String file: listFile){
            System.out.println(file);
        }
    }
}