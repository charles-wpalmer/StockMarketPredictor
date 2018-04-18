package chp38.Files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to handle the writing to files, mainly .arf files for
 * WEKA machine learning
 *
 * @author Charles Palmer
 */
public class WekaFileWriter {

    /**
     * Amount of headlines in each object
     */
    private static int headlineCount = 25;

    /**
     * The different price attributes for each object
     */
    private static String[] attributes = {"open", "high", "low", "close", "volume"};

    /**
     * The classes used for each object, either increase or decrease
     */
    private static String classes = "{decrease,increase}";

    /**
     * Method to generate a test .arf file to be used by WEKA. Passed an ArrayList of
     * data, each index has the stock prices and the sentiment value of the 25 news headlines:
     *
     *      {open, high, low, close, volume, h1 ... h25}
     *
     * @param name name of the file
     * @param data data to generate the file with
     */
    public static void generateTestArfFile(String name, ArrayList data) throws IOException {
        File file = new File("./" + name);

        file.createNewFile();

        FileWriter writer = new FileWriter(file);

        writer.write("@relation myrelation");
        writer.write(System.getProperty( "line.separator" ));

        for(String attribute : attributes){
            writer.write("@attribute " + attribute + " numeric");
            writer.write(System.getProperty( "line.separator" ));
        }

        for(int i=1; i <= headlineCount; i++){
            writer.write("@attribute headline" + i  + " numeric");
            writer.write(System.getProperty( "line.separator" ));
        }

        writer.write("@attribute class " + classes);
        writer.write(System.getProperty( "line.separator" ));

        writer.write(System.getProperty( "line.separator" ));
        writer.write("@data");

        writer.write(System.getProperty( "line.separator" ));
        writer.write(generateObject(data));

        writer.close();
    }

    /**
     * Method to build the training arff file given a lis of the string objects.
     *
     * @param name
     * @param objects
     */
    public static void generateTrainingArfFile(String name, ArrayList<String> objects) throws IOException {

        File file = new File("./" + name);

        file.createNewFile();

        FileWriter writer = new FileWriter(file);

        writer.write("@relation myrelation");
        writer.write(System.getProperty( "line.separator" ));

        for(String attribute : attributes){
            writer.write("@attribute " + attribute + " numeric");
            writer.write(System.getProperty( "line.separator" ));
        }

        for(int i=1; i <= headlineCount; i++){
            writer.write("@attribute headline" + i  + " numeric");
            writer.write(System.getProperty( "line.separator" ));
        }

        writer.write("@attribute class " + classes);
        writer.write(System.getProperty( "line.separator" ));

        writer.write(System.getProperty( "line.separator" ));
        writer.write("@data");

        writer.write(System.getProperty( "line.separator" ));

        for(String object : objects){
            writer.write(object);
            writer.write(System.getProperty( "line.separator" ));
        }

        writer.close();
    }

    /**
     * Method to build a line, relating to a sinlge object for the
     * .arf file
     *
     * @param data the data to be converted ti string
     * @return String the object
     */
    public static String generateObject(ArrayList data){
        StringBuilder object = new StringBuilder();

        for (Object value : data) {
            object.append(value);
            object.append(",");
        }

        object.deleteCharAt(object.length()-1);

        return object.toString();
    }
}
