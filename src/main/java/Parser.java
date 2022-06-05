import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Parser {


    public int[]
    getMatrixData(String file) {
        int matrixSize = getDimension(file);
        int[] matrixData = new int[matrixSize*matrixSize];
        int charIndex = 0;
        int tabIndex = 0;
        int parsedNumber=-1;
        String currentstring;
        try {
            File myFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(file)).getFile());
            Scanner scanner = new Scanner(myFile);
            String data;
            while(scanner.hasNextLine()) {
                data = scanner.nextLine().trim();
                if(data.startsWith("EDGE_WEIGHT_SECTION")) {
                    while(!data.startsWith("DISPLAY_DATA_SECTION" )&& !data.startsWith("EOF")) {
                        data=scanner.nextLine().trim();
                        while(data!="" && data.indexOf(" ")!=-1) {
                            currentstring=data.substring(charIndex,data.indexOf(" "));
                            try {
                                parsedNumber = Integer.parseInt(currentstring);
                            }
                            catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            matrixData[tabIndex] = parsedNumber;
                            tabIndex++;
                            data = data.substring(currentstring.length()+1).trim();
                        }
                        if(!data.startsWith("EOF")&&!data.startsWith("DISPLAY_DATA_SECTION")&&data!="") {
                            try {
                                parsedNumber = Integer.parseInt(data);
                            }
                            catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            matrixData[tabIndex] = parsedNumber;
                            tabIndex++;}
                    }
                }

            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return matrixData;
    }
    public Integer
    getDimension(String file) {
        int result=-1;
        String data,dimension;
        try {
            File myFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(file)).getFile());
            Scanner scanner = new Scanner(myFile);
            while(scanner.hasNextLine()) {
                data = scanner.nextLine().trim();
                if(data.startsWith("DIMENSION")) {
                    dimension = data.substring(data.lastIndexOf(" ")+1);
                    result = Integer.parseInt(dimension);
                    break;
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("brak pliku");
        }
        return result;
    }
    public  String
    getType(String file) {
        String data, type = "No type specified";
        try {
            File myFile = new File(file);
            Scanner scanner = new Scanner(myFile);
            while(scanner.hasNextLine()) {
                data = scanner.nextLine().trim();
                if(data.startsWith("TYPE")) {
                    type = data.substring(data.lastIndexOf(" ")+1);;
                    break;
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return type;
    }
    public  double[][]
    getEuc2DNodesCoordinate(String file) {
        int numberOfNodes = this.getDimension(file);
        int nodeIndex=0;;
        double x=0.0,y=0.0;
        double[][] tab = new double[numberOfNodes][2];
        String data,currentstring;
        try {
            File myFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(file)).getFile());
            Scanner scanner = new Scanner(myFile);
            while(scanner.hasNextLine()) {
                data = scanner.nextLine().trim();
                if(data.startsWith("NODE_COORD_SECTION")) {
                    data=scanner.nextLine().trim();
                    while(!data.startsWith("EOF")) {
                        currentstring=data.substring(0,data.indexOf(" "));
                        try{
                            nodeIndex = Integer.parseInt(currentstring);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                        }
                        data = data.substring(currentstring.length()+1);
                        currentstring=data.substring(0,data.indexOf(" "));
                        try{
                            x = Double.parseDouble(currentstring);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                        }
                        data = data.substring(currentstring.length()+1);
                        currentstring=data;
                        try{
                            y = Double.parseDouble(currentstring);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                        }
                        tab[nodeIndex-1][0]=x;
                        tab[nodeIndex-1][1]=y;
                        data=scanner.nextLine().trim();
                    }
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tab;

    }
    public  int[][]
    generateEuc2DMatrix(double[][] coordinates, String file) {
        int dimension = getDimension(file);
        int[][] result = new int[dimension][dimension];
        for(int i= 0 ; i<dimension;i++) {
            for(int j = 0 ; j<dimension; j++) {
                double dx = Math.abs(coordinates[i][0]-coordinates[j][0]);
                double dy = Math.abs(coordinates[i][1]-coordinates[j][1]);
                int dij = (int)(Math.sqrt(dx*dx+dy*dy)+0.5);
                result[i][j]=dij;
            }
        }
        return result;
    }
    public  int[][]
    generateMatrixFromFullMatrixData(int[] data,String file) {
        int dimension = getDimension(file);
        int iterator = 0;
        int[][] result = new int[dimension][dimension];
        for(int i = 0 ; i < dimension ; i++) {
            for(int j = 0 ; j<dimension ; j++) {
                result[i][j]=data[iterator];
                iterator++;
            }
        }
        return result;
    }
    public int[][]
    generateMatrixFromLowerDiagonalMatrixData(int[] data, String file) {
        int dimension = getDimension(file);
        int iterator = 0;
        int[][] result = new int[dimension][dimension];
        for(int i = 0 ; i < dimension ; i++) {
            for(int j = 0 ; j<=i ; j++) {
                result[i][j]=data[iterator];
                iterator++;
            }
        }
        for(int i = 0 ; i<dimension ; i++) {
            for(int j = dimension-1 ; j>=i ; j--) {
                result[i][j]=result[j][i];
            }
        }

        return result;
    }
}