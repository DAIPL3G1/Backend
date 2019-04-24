/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saferus.backend;

//The task which you want to execute
import com.saferus.backend.model.Data;
import com.saferus.backend.model.TripData;
import com.saferus.backend.repository.TripRepository;
import com.saferus.backend.repository.TripTratmentRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.saferus.backend.model.TripTratment;

@Component
public class MyTimeTask extends TimerTask {

    private TripRepository tripRepository;

    private TripTratmentRepository tripTratmentRepository;

    public MyTimeTask(ApplicationContext appContext) {
        super();
        tripRepository = appContext.getBean(TripRepository.class);
        tripTratmentRepository = appContext.getBean(TripTratmentRepository.class);
    }

    public static Date parse(String date) throws java.text.ParseException {
        //Convert String to Date
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        //this is zero time so we need to add that TZ indicator for 
        if (date.endsWith("Z")) {
            date = date.substring(0, date.length() - 1) + "GMT-00:00";
        } else {
            int inset = 6;

            String s0 = date.substring(0, date.length() - inset);
            String s1 = date.substring(date.length() - inset, date.length());

            date = s0 + "GMT" + s1;
        }
        return df.parse(date);
    }

    public static JSONObject requestToMongo(String url) throws MalformedURLException, IOException, JSONException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        int responseCode = con.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        StringBuffer response = new StringBuffer();

        if (responseCode != 404) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } else {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        }
        //print in String
        //System.out.println(response.toString());
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        return myResponse;
    }

    public static void deleteMongoData(String request) throws MalformedURLException, IOException, JSONException {
        URL url = new URL(request);
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("DELETE");
        httpCon.connect();
        int responseCode = httpCon.getResponseCode();
        System.out.println("\nSending 'DELETE' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

    }

    public static List<TripData> TripProcess(JSONObject myResponse) throws JSONException, ParseException, IOException {

        //Cria uma lista dos objetos JSON
        List<Data> dataList = new ArrayList<>();

        for (int i = 0; i < myResponse.getJSONArray("data").length(); i++) {

            //Receive Id from the Data
            int id = Integer.parseInt(myResponse.getJSONArray("data").getJSONObject(i).getString("IdVeiculo"));

            //Receive Date from the Data
            Date date = parse(myResponse.getJSONArray("data").getJSONObject(i).getString("DataHora"));

            double velocity = Double.parseDouble(myResponse.getJSONArray("data").getJSONObject(i).getString("Velocidade"));

            //Receive Start and Finish Boolean from the Data
            boolean start = Boolean.parseBoolean(myResponse.getJSONArray("data").getJSONObject(i).getString("Start"));
            boolean finish = Boolean.parseBoolean(myResponse.getJSONArray("data").getJSONObject(i).getString("Finish"));

            //Receive Latitude and Longitude from the Data
            double latitude = Double.parseDouble(myResponse.getJSONArray("data").getJSONObject(i).getString("Latitude"));
            double longitude = Double.parseDouble(myResponse.getJSONArray("data").getJSONObject(i).getString("Longitude"));

            //Receive velocity limit from the Data
            double velocityLimit = Integer.parseInt(myResponse.getJSONArray("data").getJSONObject(i).getString("LimiteVelocidade"));

            Data newData = new Data(id, date, velocity, start, finish, latitude, longitude, velocityLimit);
            dataList.add(newData);
        }

        //SORT ARRAY BY DATES
        List<Data> sorted = dataList.stream().sorted(
                (a, b)
                -> {
            return (a.getDate()).compareTo(b.getDate());
        }
        ).collect(Collectors.toList());

        //Trip List
        List<TripData> trips = new ArrayList<>();

        List<Integer> vehicles = new ArrayList<>();

        for (int i = 0; i < sorted.size(); i++) {
            if (!vehicles.isEmpty()) {
                if (!vehicles.contains(sorted.get(i).getIdVeiculo())) {
                    vehicles.add(sorted.get(i).getIdVeiculo());
                }
            } else {
                vehicles.add(sorted.get(i).getIdVeiculo());
            }
        }

        for (Integer vehicleId : vehicles) {
            List<Data> dataTrip = new ArrayList<>();
            for (Data data : sorted) {
                if (data.getIdVeiculo() == vehicleId) {
                    if ((data.isStart() == true && data.isFinish() == false) || (data.isStart() == false && data.isFinish() == false)) {
                        dataTrip.add(data);
                    } else {
                        dataTrip.add(data);
                        TripData trip = new TripData(ObjectId.get(), vehicleId, dataTrip);
                        trips.add(trip);
                        dataTrip = new ArrayList<>();
                    }
                }
            }
        }

        /* for (Integer vehicleId : vehicles) {
            deleteMongoData("https://saferusmongo.herokuapp.com/api/data/" + vehicleId);
        }*/
        return trips;
    }

    @Override
    public void run() {
        try {
            JSONObject myResponse = requestToMongo("https://saferusmongo.herokuapp.com/api/data");

            System.out.println("Response: " + myResponse.getString("message"));

            if (!myResponse.getString("message").equals("data not found")) {
                List<TripData> trips = TripProcess(myResponse);

                for (TripData trip : trips) {
                    tripRepository.save(trip);
                }
            }

            TripTratment();

        } catch (JSONException | IOException | ParseException ex) {
            System.out.println(ex);
        }
    }

    //FUNÇÕES PARA TRATAR
    //Função para tratar
    public void TripTratment() {

        System.out.print("hello");

        //Vai ver quantos veiculos tem na Viagem com os dados por tratar
        List<Integer> vehicles = new ArrayList<>();

        for (TripData tripData : tripRepository.findAll()) {
            if (!vehicles.contains(tripData.getVehicle_id())) {
                vehicles.add(tripData.getVehicle_id());
            }
        }

        System.out.println("Veiculos existentes: " + vehicles);

        //Para cada veiculo existente no Array que tem todos os veiculos que viajaram ou estão a viajar
        for (Integer vehicleId : vehicles) {
            for (TripData tripData : tripRepository.findAll()) {
                //Verifica se Viagem Com dados por tratar tem o id do veiculo desse array
                if ((tripData.getVehicle_id() == vehicleId)) {

                    Date startDate = tripData.getDatas().get(0).getDate();
                    Date finishDate = tripData.getDatas().get(tripData.datas.size() - 1).getDate();

                    double latitudeStart = tripData.getDatas().get(0).getLatitude();
                    double longitudeStart = tripData.getDatas().get(0).getLongitude();

                    double latitudeFinish = tripData.getDatas().get(tripData.datas.size() - 1).getLatitude();
                    double longitudeFinish = tripData.getDatas().get(tripData.datas.size() - 1).getLongitude();

                    long tripTime = finishDate.getTime() - startDate.getTime();

                    int distance = 0;

                    //Distancia total percorrida
                    for (int i = 0; i < tripData.getDatas().size(); i++) {
                        if (distance != 0) {
                            distance += Math.sqrt(
                                    Math.pow(tripData.getDatas().get(i).getLongitude() - tripData.getDatas().get(i - 1).getLongitude(), 2)
                                    + Math.pow(tripData.getDatas().get(i - 1).getLatitude() - tripData.getDatas().get(i).getLatitude(), 2));
                        } else {
                            distance += Math.sqrt(
                                    Math.pow(tripData.getDatas().get(i).getLongitude(), 2)
                                    + Math.pow(tripData.getDatas().get(i).getLatitude(), 2));
                        }
                    }

                    long timeAboveLimit = 0;

                    //Tempo em % acima do limite de velocidade
                    for (int i = 1; i < tripData.getDatas().size(); i++) {
                        if (tripData.getDatas().get(i).getVelocity() > tripData.getDatas().get(i).getVelocityLimit()) {
                            timeAboveLimit += tripData.getDatas().get(i).getDate().getTime()
                                    - tripData.getDatas().get(i - 1).getDate().getTime();
                        }
                    }

                    long AboveLimitAverage = 0;

                    if (tripTime == 0) {
                        AboveLimitAverage = 0;
                    } else {
                        AboveLimitAverage = timeAboveLimit / tripTime * 100;
                    }

                    int velocity = 0;

                    //Velocidade Média de uma viagem
                    for (int i = 0; i < tripData.getDatas().size(); i++) {
                        velocity += tripData.getDatas().get(i).getVelocity();

                    }

                    int velocityAverage = velocity / tripData.getDatas().size();

                    TripTratment tripTratment = new TripTratment(ObjectId.get(), vehicleId, startDate, finishDate, latitudeStart, longitudeStart, latitudeFinish, longitudeFinish, tripTime, distance, AboveLimitAverage, velocityAverage);
                    tripTratmentRepository.save(tripTratment);
                }
            }

        }
    }

}
