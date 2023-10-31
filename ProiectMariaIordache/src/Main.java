
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Apartament> listaApartamente = new ArrayList<>();
        try {
            var fisier = new File("date\\intretinere_ap.txt");
            Scanner citireAp = new Scanner(fisier).useDelimiter(",");
            while (citireAp.hasNextLine()) {
                String linie = citireAp.nextLine();
                String[] elemAp = linie.split(",");

                int nr_apartament = Integer.parseInt(elemAp[0]);
                int suprafata = Integer.parseInt(elemAp[1]);
                int nr_pers = Integer.parseInt(elemAp[2]);

                Apartament tempAp = new Apartament(nr_apartament, suprafata, nr_pers);
                listaApartamente.add(tempAp);
            }
            citireAp.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listaApartamente.forEach(System.out::println);


        int nrApSuprafataMax = 0;
        int suprafataMax = 0;
        for (Apartament a : listaApartamente) {
            if (a.getSuprafata() > suprafataMax) {
                nrApSuprafataMax = a.getNr_apartament();
                suprafataMax = a.getSuprafata();
            }
        }
        System.out.println("Nr ap cu suprafata maxima este:" + nrApSuprafataMax);


        int nrTotalPers = 0;
        for (Apartament a : listaApartamente) {
            nrTotalPers += a.getNr_persoane();
        }
        System.out.println("Nr total de persoane:" + nrTotalPers);


        List<Factura> listaFacturi = new ArrayList<>();
        try (var fisier = new FileReader("date\\intretinere_facturi.json")) {
            var jsonFacturi = new JSONArray(new JSONTokener(fisier));

            for (int i = 0; i < jsonFacturi.length(); i++) {
                var jsonFactura = jsonFacturi.getJSONObject(i);

                Factura f = new Factura(
                        jsonFactura.getString("denumire"),
                        jsonFactura.getString("repartizare"),
                        jsonFactura.getDouble("valoare"));
                listaFacturi.add(f);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listaFacturi.forEach(System.out::println);


        double valTotalaPersoane = 0;
        double valTotalAp = 0;
        double valTotalSuprafata = 0;

        for (Factura f : listaFacturi) {
            if (f.getRepartizare().equals("persoane")) {
                valTotalaPersoane += f.getValoare();
            } else if (f.getRepartizare().equals("numar_apartamente")) {
                valTotalAp += f.getValoare();
            } else if (f.getRepartizare().equals("suprafata")) {
                valTotalSuprafata += f.getValoare();
            }
        }
        System.out.println("Val totala repartizare persoane " + valTotalaPersoane);
        System.out.println("Val totala repartizare apartament " + valTotalAp);
        System.out.println("Val totala repartizare suprafata " + valTotalSuprafata);


        try (PrintWriter writer = new PrintWriter("date\\apartamente.json")) {
            JSONArray jsonArray = new JSONArray();
            listaApartamente.sort(Comparator.comparing(Apartament::getNr_apartament));

            listaApartamente.forEach(apartament -> {
                int nr = listaApartamente.stream()
                        .filter(x->x.getNr_apartament()==apartament.getNr_apartament())
                        .collect(Collectors.toList()).size();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("numar_apartamente", apartament.getNr_apartament());
                jsonObject.put("persoane", apartament.getNr_persoane());
                jsonObject.put("suprafata", apartament.getSuprafata());
                jsonArray.put(jsonObject);
            });
            writer.write(jsonArray.toString());
            System.out.println("S-a scris fisierul json");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        try(PrintWriter writer = new PrintWriter("date\\fisier.txt")) {
            listaApartamente.forEach(x-> writer.write(x.getNr_apartament() + " " + x.getNr_persoane() + " "
                    + x.getSuprafata() + " \n"));
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}