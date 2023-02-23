import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieTags {
    public static void main(String[] args) throws IOException {

        //read csv file 
        String file = "tags.csv";
        List<String> tags = new ArrayList<>(); //stores tag names

        try (BufferedReader read = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = read.readLine()) != null) {

                //separate with comma
                String[] sep = line.split(",");
                tags.add(sep[1]);
            }
        } 
        //counts frequency of tags usingr TreeMap
        Map<String, Integer> tagCounts = countTags(tags);

        //sorts tags by frequency
        List<Map.Entry<String, Integer>> sortedTags = sortTags(tagCounts);

        //list most popular tags
        System.out.println("*** Highest 3 movies by count ***");
        for (int i = 0; i < 3 && i < sortedTags.size(); i++) {
            Map.Entry<String, Integer> entry = sortedTags.get(i);
            System.out.println(entry.getKey() + " (" + entry.getValue() + ")");
        }
        //list least popular tags
        System.out.println("*** Lowest 3 movies by count ***");
        int numTags = sortedTags.size();
        for (int i = numTags - 1; i >= numTags - 3 && i >= 0; i--) {
            Map.Entry<String, Integer> entry = sortedTags.get(i);
            System.out.println(entry.getKey() + " (" + entry.getValue() + ")");
    }

        Scanner scanner = new Scanner(System.in);

        //allows user to search for tags by count or find count of a certain tag
        while (true) {
            System.out.print("(Search by Tag or Tag Count? (Enter T or C... or EXIT to exit): ");
            String input = scanner.nextLine().trim();

            //search tags by count ignoring cases for user input
            if (input.equalsIgnoreCase("C")) {
                System.out.print("Count to search for: ");
                int count = Integer.parseInt(scanner.nextLine().trim());
                List<String> tagsByCount = findTagsByCount(tagCounts, count);
                System.out.print("Tags with " + count + " occurences: ");

                for (String tag : tagsByCount) {
                    System.out.println(tag);
                }

                //find count of ceratin tag ignoring cases for easy user input
            } else if (input.equalsIgnoreCase("T")) {
                System.out.print("Tag to search for: ");
                String tag = scanner.nextLine().trim();
                int count = tagCounts.getOrDefault(tag, 0);
                System.out.println("Tag occured " + count + " times.");

            //exit program
            } else if (input.equalsIgnoreCase("EXIT")) {
                break;
            }

            //invalid user input
            else {
                System.out.println("Invalid input.");
            }
        }
    }

    //counts frquency of tags with TreeMap
    private static Map<String, Integer> countTags(List<String> tags) {

        Map<String, Integer> counts = new TreeMap<>();

        for (String tag : tags) {
            counts.put(tag, counts.getOrDefault(tag, 0) + 1);
        }
        return counts;
    }

    //sorts tags by frequency
    private static List<Map.Entry<String, Integer>> sortTags(Map<String, Integer> tagCounts) {

        //stores tags and their counts
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(tagCounts.entrySet());

        //sort list in ascending order
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {

            @Override 
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {

                int sort = e2.getValue().compareTo(e1.getValue()); // sort by decreasing frequency

                if (sort == 0) {
                    sort = e1.getKey().compareTo(e2.getKey()); // if tied, use alphabetical order
                }
                return sort;
            }
        });

        return entries;
    }
    // finds tags that appear a certain number of times
    private static List<String> find(Map<String, Integer> tagCounts, int count) {

        List<String> tags = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
            if (entry.getValue() == count) {
                tags.add(entry.getKey());
            }
        }
        return tags;
    }
}
