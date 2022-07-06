package storage.offer;

import beans.offer.Offer;
import beans.sportfacility.SportFacility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class OfferStorage {

    private static OfferStorage instance = null;

    public static OfferStorage getInstance() {
        if (instance == null) {
            instance = new OfferStorage();
        }
        return instance;
    }

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private OfferStorage() {
    }

    public List<Offer> getAll() {
        List<Offer> allOffers = new ArrayList<>();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/offers.json"));
            allOffers = gson.fromJson(reader, new TypeToken<List<Offer>>() {}.getType());
            reader.close();
            return allOffers;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allOffers;
    }

    public Offer getById(int id) {
        return getAll().stream().filter(offer -> offer.getId() == id).findFirst().orElse(null);
    }

    public Offer update(Offer offer) {
        List<Offer> offers = getAll();
        for(int i = 0; i < offers.size(); i++) {
            if(offers.get(i).getId() == offer.getId()) {
                offers.set(i, offer);
                save(offers);
                return offer;
            }
        }
        return null;
    }

    private void save(List<Offer> offers) {
        try(FileWriter writer =new FileWriter("./storage/offers.json")){
            gson.toJson(offers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getId() {
        return getAll().size();
    }

    public Offer add(Offer offer) {
        List<Offer> offers = getAll();
        int id = getId();
        try(FileWriter writer =new FileWriter("./storage/offers.json")){
            offer.setId(id);
            offers.add(offer);
            gson.toJson(offers, writer);
            return offer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
