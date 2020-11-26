import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.*;

class API {
    public static void main(String[] args) throws IOException, JSONException {
        ArrayList<Person> people = new ArrayList<>();
        String api = "https://api.vk.com/method/groups.getMembers?" +
                "group_id=iritrtf_urfu&fields=city,sex,bdate,universities&access_token=" +
                "f5493d1ef5493d1ef5493d1e03f53ddc23ff549f5493d1eaae58a467485251565e27d2a&v=5.126";
        URL url = new URL(api);
        URLConnection connection = url.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = readAll(rd);
        rd.close();

        JSONObject members = new JSONObject(line);
        JSONArray arrayOfMembers = members.getJSONObject("response").getJSONArray("items");

        for (int i=0; i < arrayOfMembers.length(); i++) {
            JSONObject info = arrayOfMembers.getJSONObject(i);
            Object firstName;
            Object lastName;
            firstName = info.get("first_name");
            lastName = info.get("last_name");
            Object sex;
            String cityName;
            Object university;
            sex = info.get("sex").equals(1) ? "Women": "Man";

            try {
                JSONObject city = info.getJSONObject("city");
                cityName = city.get("title").toString();
            } catch (Exception e) {
                cityName = "no information";
            }

            try {
                JSONObject education = info.getJSONArray("universities").getJSONObject(0);
                university = education.get("name");
            } catch (Exception e) {
                university = "no information";
            }

            if (!firstName.equals("DELETED"))
                people.add(new Person(firstName, lastName, sex, cityName, university));
        }

        // Сериализация:
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("person.dat")))
        {
            for (Person person: people) {
                oos.writeObject(person);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

//      Для вывода коллекции:
//        for (Person person: people) {
//            System.out.println("Name: " + person.firstName + " " + person.lastName + ", city: "
//                    + person.city + ", sex: " + person.sex + ", education: " + person.university);
//        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}