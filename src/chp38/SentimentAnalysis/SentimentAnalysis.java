package chp38.SentimentAnalysis;
import com.aliasi.util.Files;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;

import com.aliasi.lm.NGramProcessLM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to handle communication with Lingpipe, and analyse
 * the news headlines.
 *
 * @author Charles Palmer
 */
public class SentimentAnalysis {

    File mPolarityDir;
    String[] mCategories;
    ArrayList<NewsHeadline> testFiles;
    DynamicLMClassifier<NGramProcessLM> mClassifier;

    public SentimentAnalysis(String dir) {
        testFiles = new ArrayList<NewsHeadline>();
        mPolarityDir = new File(dir,"news_headlines");
        mCategories = mPolarityDir.list();
        int nGram = 8;
        mClassifier = DynamicLMClassifier.createNGramProcess(mCategories,nGram);
    }

    /**
     * Allows for CV. If the fold is 9 (i.e file is 900+) then use it as test
     * Data.
     *
     * @param file
     * @return
     */
    boolean isTrainingFile(File file) {
        return file.getName().charAt(2) != '9';  // test on fold 9
    }

    /**
     * Class to handle news headlines from multiple
     * files, train the model, and evaluate the test
     * data.
     *
     * @param folder
     * @throws IOException
     */
    public void handleFromFolder(String folder) throws IOException {
        mPolarityDir = new File(folder,"news_headlines");
        mCategories = mPolarityDir.list();

        for (int i = 0; i < mCategories.length; ++i) {
            File file = new File(mPolarityDir, mCategories[i]);
            File[] allFiles = file.listFiles();

            Classification classification = new Classification(mCategories[i]);


            for (int j = 0; j < allFiles.length; ++j) {
                File trainFile = allFiles[j];
                String headline = Files.readFromFile(trainFile, "ISO-8859-1");

                if (isTrainingFile(trainFile)) {
                    train(headline, classification);
                } else {
                    // Build test data with the nth fold
                    testFiles.add(new NewsHeadline(headline, mCategories[i]));
                }
            }
        }
        evaluateTraining();
    }

    /**
     * Class to handle news headlines from a single (csv)
     * file, and then train the model
     *
     * Todo:
     *  - Need to do cross validation - how?
     * @param folder
     * @throws IOException
     */
    public void handleFromFile(String folder) throws IOException {
        mPolarityDir = new File(folder,"news_headlines");
        mCategories = mPolarityDir.list();
        String line;

        for (int i = 0; i < mCategories.length; ++i) {
            Classification classification = new Classification(mCategories[i]);
            String csvFile = folder + "news_headlines/" + mCategories[i] + "/" + mCategories[i] + ".csv";

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

                while ((line = br.readLine()) != null) {

                    String[] headline = line.split(",");

                    train(headline[1], classification);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Generate a list of test news headlines, to test the model
     *
     * @param data
     */
    public void prepareTrainingFiles(ArrayList data){
        for(int i=0; i < data.size(); i++){

            NewsHeadline temp = new NewsHeadline(data.get(i).toString(), "");

            this.testFiles.add(temp);
        }
    }

    /**
     * Method to add a single piece of data to the test dataset.
     * To allow recursive building of the data to be external from
     * this file.
     *
     * @param headline
     */
    public void addTestData(String headline){
        NewsHeadline newNews = new NewsHeadline(headline, "");

        this.testFiles.add(newNews);
    }

    /**
     * Method to add a single piece of data to the training model.
     * To allow recursive building of the model be external from this
     * file.
     *
     * @param headline
     * @param category
     */
    public void addToTraining(String headline, String category){
        Classification classification = new Classification(category);

        train(headline, classification);
    }

    /**
     * Evaluate using CV so we can check if the model has got it
     * correct
     */
    void evaluateTraining(){
        int numCorrect = 0;

        for (int i=0; i < this.testFiles.size(); i++) {
            NewsHeadline temp = this.testFiles.get(i);

            Classification classification
                    = mClassifier.classify((CharSequence) temp.getHeadline());

            if (classification.bestCategory().equals(temp.getClassification())) {
                ++numCorrect;
            }

        }

        System.out.println("  # Correct=" + numCorrect);
        System.out.println("  % Correct="
                + ((double)numCorrect)/(double)200);
    }

    /**
     * Evaluate a whole new news headline that we 'don't know' the classification of
     * And display what is was classified as
     */
    public void evaluateTest(){
        for (int i=0; i < this.testFiles.size(); i++) {
            NewsHeadline temp = this.testFiles.get(i);

            Classification classification
                    = mClassifier.classify((CharSequence) temp.getHeadline());

            System.out.println(temp.getHeadline() + "------" + classification.bestCategory());

        }
    }

    /**
     * Add news headlines to the model
     *
     * @param headline
     * @param classification
     */
    void train(String headline, Classification classification){
        Classified<CharSequence> classified
                = new Classified<CharSequence>((CharSequence) headline, classification);

        mClassifier.handle(classified);
    }

    /**
    double calculatePMI(double wordOne, double wordTwo){
        double PMI;



        return PMI;
    }
    */

}