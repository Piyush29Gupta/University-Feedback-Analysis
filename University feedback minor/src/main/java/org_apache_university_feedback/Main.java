package org_apache_university_feedback;

import com.opencsv.CSVReader;

import java.io.FileReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.PropertiesUtils;

import java.io.IOException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;


import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

class Csv {



    private CSVReader file;

    private String[] line;

    private String header;


    public Csv() throws Exception {
        String csv_path = "C:\\Users\\Piyush Gupta\\OneDrive\\Desktop\\University feedback minor\\src\\main\\java\\org_apache_university_feedback\\College_Feedback.csv";


        file = new CSVReader(new FileReader(csv_path));

        line = file.readNext();

        header = line[0];

    }

    public String[] Get_data() throws Exception {
        line = file.readNext();

        if (line != null) {

            return line;
        }



        return null;
    }

    public void csv_close() throws Exception {

        if (file != null) {
            file.close();
        }

    }


}

public class Main {

    public static int sr_no = 0;

    public static void main(String[] args) throws Exception {

        int[] column_b = new int[5];
        int[] column_c = new int[5];
        int[] column_d = new int[5];
        int[] column_e = new int[5];

        try{

            // MONGODB CONNECTION

//            var mongoClient = MongoClients.create("mongodb://localhost:27017/");
//            MongoDatabase database = mongoClient.getDatabase("student_feedback");
//            MongoCollection<Document> collection_a = database.getCollection("column_a");
//
//            MongoCollection<Document> collection_b = database.getCollection("column_b");
//
//            MongoCollection<Document> collection_c = database.getCollection("column_c");
//
//            MongoCollection<Document> collection_d = database.getCollection("column_d");
//
//            MongoCollection<Document> collection_e = database.getCollection("column_e");

            Csv csv_file = new Csv();

            String[] csvString;

            while ((csvString = csv_file.Get_data()) != null) {

                String dataString = csvString[0];

                int column_data;
                column_data = Integer.parseInt(csvString[1]);

                if(column_data == 1){
                    column_b[0]++;
                }
                else if(column_data == 2){
                    column_b[1]++;
                }
                else if(column_data == 3){
                    column_b[2]++;
                }
                else if(column_data == 4){
                    column_b[3]++;
                }
                else if(column_data ==5){
                    column_b[4]++;
                }


                column_data = Integer.parseInt(csvString[2]);

                if(column_data == 1){
                    column_c[0]++;
                }
                else if(column_data == 2){
                    column_c[1]++;
                }
                else if(column_data == 3){
                    column_c[2]++;
                }
                else if(column_data == 4){
                    column_c[3]++;
                }
                else if(column_data ==5){
                    column_c[4]++;
                }

                column_data = Integer.parseInt(csvString[3]);

                if(column_data == 1){
                    column_d[0]++;
                }
                else if(column_data == 2){
                    column_d[1]++;
                }
                else if(column_data == 3){
                    column_d[2]++;
                }
                else if(column_data == 4){
                    column_d[3]++;
                }
                else if(column_data ==5){
                    column_d[4]++;
                }

                column_data = Integer.parseInt(csvString[4]);

                if(column_data == 1){
                    column_e[0]++;
                }
                else if(column_data == 2){
                    column_e[1]++;
                }
                else if(column_data == 3){
                    column_e[2]++;
                }
                else if(column_data == 4){
                    column_e[3]++;
                }
                else if(column_data ==5){
                    column_e[4]++;
                }




//                 PRINT EACH ROW
                System.out.print(sr_no + " ROW DATA : " + dataString);


                sr_no++;

                String remove_stopword_dataString = Perform_stopword_remove(dataString);

                String lemmatization_dataString = perform_Lemmatization(remove_stopword_dataString);

                int score = score(lemmatization_dataString);

//                Document feedbackDoc = new Document("datastring", dataString).append("score", score);
//                collection_a.insertOne(feedbackDoc);



                switch(score){
                    case 1:
                        System.out.println("  --Positive.");
                        break;

                    case 2:
                        System.out.println("  --Negative.");
                        break;

                    case 0:
                        System.out.println("  --Natural.");
                        break;

                    default:
                        break;
                }


                System.out.println("\n \n");


            }


//            Document feedbackDoc_b = new Document("one",column_b[0]).append("two",column_b[1]).append("three",column_b[2]).append("four",column_b[3]).append("five",column_b[4]);
//            collection_b.insertOne(feedbackDoc_b);
//
//            Document feedbackDoc_c = new Document("one",column_c[0]).append("two",column_c[1]).append("three",column_c[2]).append("four",column_c[3]).append("five",column_c[4]);
//            collection_c.insertOne(feedbackDoc_c);
//
//            Document feedbackDoc_d = new Document("one",column_d[0]).append("two",column_d[1]).append("three",column_d[2]).append("four",column_d[3]).append("five",column_d[4]);
//            collection_d.insertOne(feedbackDoc_d);
//
//            Document feedbackDoc_e = new Document("one",column_e[0]).append("two",column_e[1]).append("three",column_e[2]).append("four",column_e[3]).append("five",column_e[4]);
//            collection_e.insertOne(feedbackDoc_e);




            csv_file.csv_close();

        } catch (Exception e) {
            System.out.println("FOUND AN EXCEPTION");
        }


    }

    public static String Perform_stopword_remove(String line) throws Exception {



        ArrayList<String> stopWords = new ArrayList<>(Arrays.asList("a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are",
                "aren't", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both",
                "but", "by", "can", "can't", "cannot", "could", "couldn't", "did", "didn't", "do", "does",
                "doesn't", "doing", "don't", "down", "during", "each", "few", "for", "from", "further",
                "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he", "he'd", "he'll", "he's",
                "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i",
                "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it", "it's", "its", "itself",
                "let's", "me", "more", "most", "mustn't", "my", "myself", "no", "nor", "not", "of", "off",
                "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over",
                "own", "same", "shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't", "so",
                "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves",
                "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've",
                "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn't",
                "we", "we'd", "we'll", "we're", "we've", "were", "weren't", "what", "what's", "when",
                "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's",
                "with", "won't", "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your",
                "yours", "yourself", "yourselves"
        ));

        ArrayList<String> removeStopWordsList = new ArrayList<>();


        String[] words = line.split("\\s");

        for (String word : words) {
            String lowerCaseWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();

            if (!stopWords.contains(lowerCaseWord)) {
                removeStopWordsList.add(word);
            }

        }


        return String.join(" ", removeStopWordsList);


    }

    public static String perform_Lemmatization(String text) {

        StanfordCoreNLP pipeline = new StanfordCoreNLP(PropertiesUtils.asProperties(
                "annotators", "tokenize,ssplit,pos,lemma",
                "ssplit.isOneSentence", "true",
                "tokenize.language", "en"));

        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);

        StringBuilder lemmatized_Text = new StringBuilder();
        for (CoreLabel token : document.tokens()) {
            lemmatized_Text.append(token.get(CoreAnnotations.LemmaAnnotation.class)).append(" ");
        }

        return lemmatized_Text.toString().trim();
    }

    public static int score(String data) throws Exception {
        ArrayList<String> positiveWords = new ArrayList<>(Arrays.asList(
                "excellent", "growth", "liked", "top-notch", "abundant", "well-maintained",
                "good", "supportive", "knowledgeable", "safe", "secure", "balanced",
                "approachable", "great", "encouraging", "effective", "valuable",
                "informative", "accessible", "enjoyable", "adequate", "satisfied",
                "friendly", "engaging", "helpful", "motivating", "respectful"
        ));

        ArrayList<String> negativeWords = new ArrayList<>(Arrays.asList(
                "unapproachable", "unhelpful", "poor", "lacking", "average", "bad",
                "needs attention", "concern", "not enough", "improvement", "required",
                "efforts", "very poor", "okay", "nothing exceptional",
                "room for improvement", "upgrade", "disappointing", "inadequate",
                "frustrating", "complicated", "unsatisfactory", "unsafe", "limited",
                "stressful", "overwhelming", "strict", "unavailable", "biased", "unconvineient"
        ));

        String positive = perform_Lemmatization(String.join(" ", positiveWords));
        String negative = perform_Lemmatization(String.join(" ", negativeWords));

        positiveWords = new ArrayList<>(Arrays.asList(positive.split(" ")));
        negativeWords = new ArrayList<>(Arrays.asList(negative.split(" ")));

        String[] words = data.split("\\s+");

        int score = 0;
        for (String word : words) {
            if (positiveWords.contains(word)) {
                score = 1;
            }
            if (negativeWords.contains(word)) {
                return 2;
            }
        }

        return score;
    }



}

