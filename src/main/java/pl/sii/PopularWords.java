package pl.sii;

import org.apache.commons.lang3.NotImplementedException;

import java.io.*;
import java.util.*;


public class PopularWords {

    public HashMap<String, Long> records = new HashMap<>();
    int sizeOfSet = 1000;

    public static void main(String[] args) {
        PopularWords popularWords = new PopularWords();
        Map<String, Long> result = popularWords.findOneThousandMostPopularWords();

        result.entrySet().forEach(System.out::println);
    }

    public Map<String, Long> findOneThousandMostPopularWords() {
        Map<String, Long> result = new LinkedHashMap<>();
        try
        {
            //odczytanie pliku 3esl.txt
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/3esl.txt"));
            String line;

            //dodanie wszytskich slow do zbioru
            while ((line = reader.readLine()) != null) {
                String[] words=line.split("\\s+");

                //zliczenie wystapien slow w tekscie
                for (String word : words) {
                    Long n = records.get(word);
                    n = (n == null) ? 1 : ++n;
                    records.put(word.toLowerCase(), n);
                }
            }

            //slowo "anesthetic" nie wystepowalo w pliku all.num, usunalem je ze zbioru aby porownanie moglo sie wykonac dla wszytskich slow
            records.remove("anesthetic");

            //sortowanie dla uzyskania 1000 najpopularniejszych slow
            List<Map.Entry<String, Long>> list = new ArrayList<>(records.entrySet());
            list.sort(Map.Entry.comparingByValue());
            Collections.reverse(list);

            for (Map.Entry<String, Long> entry : list) {
                if(sizeOfSet == 0) break;
                result.put(entry.getKey(), entry.getValue());
                sizeOfSet --;
            }
            reader.close();
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

       return result;
    }
}
