package storage;

import beans.users.PromoCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PromoCodeStorage {
    private static PromoCodeStorage instance = null;

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public static PromoCodeStorage getInstance() {
        if (instance == null) {
            instance = new PromoCodeStorage();
        }
        return instance;
    }

    private PromoCodeStorage() {
    }

    public PromoCode getByCode(String code) {
        List<PromoCode> promoCodes = getAll();
        for (PromoCode promo:promoCodes) {
            if(promo.getCode().equals(code)) return promo;

        }
        return null;
    }

    public PromoCode add(PromoCode promoCode) {
        List<PromoCode> promoCodes = getAll();
        try(FileWriter writer =new FileWriter("./storage/promoCode.json")){
            promoCodes.add(promoCode);
            gson.toJson(promoCodes, writer);
            return promoCode;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void edit(PromoCode promoCode) {
        List<PromoCode> promoCodes = getAll();
        for (PromoCode promo: promoCodes) {
            if (promo.getCode().equals(promoCode.getCode())) {
                promo.setCode(promoCode.getCode());
                promo.setEndDate(promoCode.getEndDate());
                promo.setStartDate(promoCode.getStartDate());
                promo.setUsageTimes(promoCode.getUsageTimes());
                promo.setDiscount(promoCode.getDiscount());
                break;
            }
        }
        save(promoCodes);
    }
        public List<PromoCode> getAll() {
            List<PromoCode> promoCodes = new ArrayList<>();
            try {
                Reader reader = Files.newBufferedReader(Paths.get("./storage/promoCode.json"));
                promoCodes = gson.fromJson(reader, new TypeToken<List<PromoCode>>() {}.getType());
                reader.close();
                return promoCodes;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        public List<PromoCode> getAllActive(LocalDate currentDate) {
            List<PromoCode> promoCodes = new ArrayList<>();
            for (var promoCode : getAll()) {

                if(promoCode.getStartDate().minusDays(1).isBefore(currentDate) && currentDate.isBefore(promoCode.getEndDate())
                && promoCode.getUsageTimes()>0) {
                    promoCodes.add(promoCode);
                }
            }
            return promoCodes;
        }

        private void save(List<PromoCode> promoCodes) {
            try(FileWriter writer =new FileWriter("./storage/promoCode.json")){
                gson.toJson(promoCodes, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
