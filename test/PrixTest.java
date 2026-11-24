import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PrixTest {

    static int echecs = 0;
    static List<String> cas = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        verifie("ttc_100_18", Prix.ttc(100, 18) == 118.0);
        verifie("ttc_taux_zero", Prix.ttc(50, 0) == 50.0);
        verifie("remise_200_10", Prix.remise(200, 10) == 180.0);
        verifie("remise_100_pourcent", Prix.remise(80, 100) == 0.0);
        verifie("total_25_x4", Prix.total(25, 4) == 100.0);
        verifie("ttc_negatif_refuse", leveErreur(() -> Prix.ttc(-1, 18)));
        verifie("remise_150_refusee", leveErreur(() -> Prix.remise(100, 150)));

        rapport("PrixTest");
        System.out.println(cas.size() + " tests, " + echecs + " failure(s). Report: build\\test-results\\TEST-PrixTest.xml");
    }

    static void verifie(String nom, boolean ok) {
        System.out.println((ok ? "PASS  " : "FAIL  ") + nom);
        cas.add(nom + (ok ? "" : "!"));
        if (!ok) echecs++;
    }

    static boolean leveErreur(Runnable r) {
        try { r.run(); return false; } catch (IllegalArgumentException e) { return true; }
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