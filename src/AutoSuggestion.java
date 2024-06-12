import java.util.*;

public class AutoSuggestion {

    // Function to compute the edit distance between two strings
    public static int editDistance(String s1, int m, String s2, int n) {
        if (m == 0) return n;
        if (n == 0) return m;

        if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
            return editDistance(s1, m - 1, s2, n - 1);
        }

        int insert = editDistance(s1, m, s2, n - 1);
        int remove = editDistance(s1, m - 1, s2, n);
        int replace = editDistance(s1, m - 1, s2, n - 1);

        return 1 + Math.min(insert, Math.min(remove, replace));
    }

    public static List<String> getSuggestions(List<String> dictionary, String word) {
        // Create a priority queue to keep track of the smallest edit distances
        PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int dist1 = editDistance(o1, o1.length(), word, word.length());
                int dist2 = editDistance(o2, o2.length(), word, word.length());
                return Integer.compare(dist1, dist2);
            }
        });

        pq.addAll(dictionary);

        // Extract the top 3 words with the smallest edit distances
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 3 && !pq.isEmpty(); i++) {
            result.add(pq.poll());
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input dictionary
        System.out.println("Enter the number of words in the dictionary:");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        List<String> dictionary = new ArrayList<>();
        System.out.println("Enter the words:");
        for (int i = 0; i < n; i++) {
            dictionary.add(scanner.nextLine());
        }

        // Input search word
        System.out.println("Enter the word to search:");
        String word = scanner.nextLine();

        // Get suggestions
        List<String> suggestions = getSuggestions(dictionary, word);

        // Output suggestions
        System.out.println("Suggestions:");
        for (String suggestion : suggestions) {
            System.out.println(suggestion);
        }

        scanner.close();
    }
}