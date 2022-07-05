package storage;

import beans.offer.Offer;
import beans.sportfacility.SportFacility;
import beans.users.Manager;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SportFacilityStorage {

    private static SportFacilityStorage instance = null;


    private static List<SportFacility> cache = new ArrayList<>();

    public static SportFacilityStorage getInstance() {
        if (instance == null) {
            instance = new SportFacilityStorage();
        }
        return instance;
    }

    private SportFacilityStorage() {
    }

    Gson gson = new GsonBuilder()
            .setExclusionStrategies(new OfferExcluder())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public SportFacility getById(int id) {
        return getAll().stream().filter(sportFacility -> sportFacility.getId() == id).findFirst().orElse(null);
    }

    public List<SportFacility> getAll() {
        List<SportFacility> sportFacilities = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/sportFacilities.json"));
            // convert JSON array to list of users
            sportFacilities = gson.fromJson(reader, new TypeToken<List<SportFacility>>() {}.getType());
            // print users
            // close reader
            reader.close();
            return sportFacilities;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sportFacilities;
    }

    public SportFacility create(SportFacility facility){
        List<SportFacility> sportFacilities = getAll();
        int id=getCount()+1;
        facility.setId(id);
        System.out.println(facility.getId());

        try(FileWriter writer =new FileWriter("./storage/sportFacilities.json")){
            sportFacilities.add(facility);
            gson.toJson(sportFacilities, writer);
            writer.close();
            return facility;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(SportFacility facility) {
        List<SportFacility> allFacilities = getAll();
        for(int i = 0;i<allFacilities.size();i++) {
            if(allFacilities.get(i).getId() == facility.getId()) {
                allFacilities.set(i, facility);
                save(allFacilities);
                break;
            }
        }
    }

    private void save(List<SportFacility> sportFacilities) {
        try(FileWriter writer =new FileWriter("./storage/sportFacilities.json")){
            gson.toJson(sportFacilities, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getCount() {
        List<SportFacility> sportFacilities = getAll();
        return sportFacilities.size();
    }

    private static class OfferExcluder implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getDeclaringClass() == Offer.class && !fieldAttributes.getName().equals("id");
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }

}
