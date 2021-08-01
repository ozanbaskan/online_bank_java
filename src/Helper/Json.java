package Helper;

import Model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Json {


    public static boolean writeOverUsers(JSONArray object)
    {
        boolean isSuccess = false;

        try (FileWriter file = new FileWriter("user.json")) {
            file.write(object.toJSONString());
            file.close();
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;

    }

    public static boolean addUser(JSONObject addObject) {
        JSONParser parser = new JSONParser();

        JSONArray jsonArray = null;
        boolean isSuccess = false;
        try {
            jsonArray = (JSONArray) parser.parse(new FileReader("user.json"));
            jsonArray.add(addObject);
            try (FileWriter file = new FileWriter("user.json")) {
                file.write(jsonArray.toJSONString());
                file.close();
                isSuccess = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    public static boolean writeOverBanks(JSONArray object)
    {
        boolean isSuccess = false;

        try (FileWriter file = new FileWriter("bank.json")) {
            file.write(object.toJSONString());
            file.close();
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;

    }

    public static boolean addBank(JSONObject addObject) {
        JSONParser parser = new JSONParser();

        JSONArray jsonArray = null;
        boolean isSuccess = false;
        try {
            jsonArray = (JSONArray) parser.parse(new FileReader("bank.json"));
            jsonArray.add(addObject);
            try (FileWriter file = new FileWriter("bank.json")) {
                file.write(jsonArray.toJSONString());
                file.close();
                isSuccess = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    public static User checkLogin(String TCId, String password)
    {
        JSONParser parser = new JSONParser();

        User user = null;

        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("user.json"));
            for (Object object : jsonArray)
            {
                JSONObject jsonUser = (JSONObject) parser.parse(String.valueOf(object));
                if (jsonUser.get("TCId").equals(TCId) && jsonUser.get("password").equals(password))
                {
                    String date = (String) jsonUser.get("birthDate");
                    int year = Integer.parseInt(date.substring(0,4));
                    int month = Integer.parseInt(date.substring(5,7));
                    int day = Integer.parseInt(date.substring(8,10));
                    user = User.get(String.valueOf(jsonUser.get("TCId")), String.valueOf(jsonUser.get("password")), (Long) jsonUser.get("balance"), LocalDate.of(year, month, day));
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        if (user == null) System.out.println("Giriş başarısız.");

        return user;
    }

    public static User getByTC(String TCId)
    {
        JSONParser parser = new JSONParser();

        User user = null;

        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("user.json"));
            for (Object object : jsonArray)
            {
                JSONObject jsonUser = (JSONObject) parser.parse(String.valueOf(object));
                if (jsonUser.get("TCId").equals(TCId))
                {
                    String date = (String) jsonUser.get("birthDate");
                    int year = Integer.parseInt(date.substring(0,4));
                    int month = Integer.parseInt(date.substring(5,7));
                    int day = Integer.parseInt(date.substring(8,10));
                    user = User.get(String.valueOf(jsonUser.get("TCId")), String.valueOf(jsonUser.get("password")), (Long) jsonUser.get("balance"), LocalDate.of(year, month, day));
                    return user;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Bu kullanıcı bulunamadı.");
        return null;
    }

    public static boolean upgradeUser(User user)
    {
        JSONParser parser = new JSONParser();

        if (user == null) return false;
        boolean isSuccess = false;

        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("user.json"));
            for (Object object : jsonArray)
            {
                JSONObject jsonUser = (JSONObject) parser.parse(String.valueOf(object));
                if (jsonUser.get("TCId").equals(user.getTCId()))
                {
                    System.out.println(user.getBalance());
                    jsonUser.put("balance", user.getBalance());
                }
                jsonArray.set(jsonArray.indexOf(object), jsonUser.toJSONString());
            }

            FileWriter file = new FileWriter("user.json");
            file.write(jsonArray.toJSONString());
            file.close();
            isSuccess = true;



        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    public static boolean isExists(User user)
    {
        JSONParser parser = new JSONParser();

        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("user.json"));
            for (Object object : jsonArray)
            {
                JSONObject jsonUser = (JSONObject) parser.parse(String.valueOf(object));
                if (jsonUser.get("TCId").equals(user.getTCId()))
                {
                    return true;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


}
