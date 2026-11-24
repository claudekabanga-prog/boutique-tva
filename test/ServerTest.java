import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ServerTest {

    static int echecs = 0;
    static List<String> cas = new ArrayList<>();
    static HttpClient client = HttpClient.newHttpClient();
    static int port = 18090;

    public static void main(String[] args) throws Exception {
        HttpServer server = PrixServer.start(port);
        try {
            verifie("http_health", get("/health").equals("200 OK"));
            verifie("http_ttc_100_18", get("/ttc/100/18").equals("200 118.00"));
            verifie("http_remise_200_10", get("/remise/200/10").equals("200 180.00"));
            verifie("http_remise_invalide_400", get("/remise/200/150").startsWith("400"));
            verifie("http_page_inconnue_404", get("/nimporte").startsWith("404"));
        } finally {
            server.stop(0);
        }

        rapport("ServerTest");
        System.out.println(cas.size() + " tests, " + echecs + " failure(s). Report: build\\test-results\\TEST-ServerTest.xml");
    }

    // Renvoie "code corps", par exemple "200 118.00"
    static String get(String chemin) throws Exception {
        HttpRequest req = HttpRequest.newBuilder(URI.create("http://localhost:" + port + chemin)).build();
        HttpResponse<String> rep = client.send(req, HttpResponse.BodyHandlers.ofString());
        return rep.statusCode() + " " + rep.body();
    }

    static void verifie(String nom, boolean ok) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + nom);
        cas.add(nom + (ok ? "" : "!"));
        if (!ok) echecs++;
    }

    static void rapport(String suite) throws Exception {
        new File("build/test-results").mkdirs();
        try (PrintWriter w = new PrintWriter("build/test-results/TEST-" + suite + ".xml", "UTF-8")) {
            w.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            w.println("<testsuite name=\"" + suite + "\" tests=\"" + cas.size() + "\" failures=\"" + echecs + "\">");
            for (String c : cas) {
                boolean ko = c.endsWith("!");
                String nom = ko ? c.substring(0, c.length() - 1) : c;
                w.println("  <testcase classname=\"" + suite + "\" name=\"" + nom + "\">"
                        + (ko ? "<failure message=\"echec\"/>" : "") + "</testcase>");
            }
            w.println("</testsuite>");
        }
    }
}