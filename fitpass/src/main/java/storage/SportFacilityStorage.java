package storage;

import beans.Address;
import beans.Location;
import beans.SportFacility;
import beans.SportFacilityType;
import beans.users.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utilities.WorkingHours;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SportFacilityStorage {

    private static SportFacilityStorage instance = null;
    Gson gson = new Gson();

    private static List<SportFacility> cache = new ArrayList<>();

    public static SportFacilityStorage getInstance() {
        if (instance == null) {
            instance = new SportFacilityStorage();
        }
        return instance;
    }

    private SportFacilityStorage() {
    }

    public List<SportFacility> getAll() {
        List<SportFacility> sportFacilities = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get("./storage/sportFacilities.json"));
            // convert JSON array to list of users
            sportFacilities = new Gson().fromJson(reader, new TypeToken<List<SportFacility>>() {}.getType());
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

    public boolean create(SportFacility facility){
        List<SportFacility> sportFacilities = getAll();
        int id=getCount()+1;
        facility.setId(id);
        System.out.println(facility.getId());

        try(FileWriter writer =new FileWriter("./storage/sportFacilities.json")){
            sportFacilities.add(facility);
            new Gson().toJson(sportFacilities, writer);
            writer.close();
            return true;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getCount() {
        List<SportFacility> sportFacilities = getAll();
        return sportFacilities.size();
    }

}
