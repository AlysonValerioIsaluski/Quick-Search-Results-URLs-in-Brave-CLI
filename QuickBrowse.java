import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class QuickBrowse {

  public static void main(String[] args) {

    final int args_size = args.length;
    if (args_size == 0) {
      System.out.println("Search failed, please inform your search as arguments");
      return;
    }

    String search = args[0];

    for (int i = 1; i < args_size; i++) {
      search += '+' + args[i];
    }

    search = search.replaceAll(" ", "+");

    try {
      URL url = java.net.URI.create("https://search.brave.com/search?q=" + search).toURL();

      findSearches(url);
    } catch (IOException e) {
      System.err.println(e.toString());
    }
  }

  private static void findSearches(URL url) {

    // Attempts URL connection and automatically closes scanner
    try (Scanner input = new Scanner(url.openStream())) {

      while (input.hasNextLine()) {
        String[] parts = input.nextLine().strip().split("\"|'");

        for (String part : parts)
          if (part.contains("https://www"))
            System.out.println(part);
      }
    } catch (IOException e) {
      System.out.println("Brave Connection failed.");
    }
  }
}
