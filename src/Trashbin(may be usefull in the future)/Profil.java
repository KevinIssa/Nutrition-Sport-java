package ulb.models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Profil{
    /**
    * Class used to store data about the user
    * this class will need to have static size variable
    * at the start of the program, the program will load a specific file in resources.ulb.database called user.txt
    * if it exists else the user will ask for data about the user
    * at the end of the program, the program will save the user data in the file.
    * */
    String lastname;
    String firstname;
    String sex;
    LocalDate birthDay;
    Database database = new Database();

    public Profil(String lastname, String firstname, String sex, LocalDate birthDay){
        this.firstname = firstname;
        this.lastname = lastname;

        this.sex = sex;
        this.birthDay = birthDay;
    }
    public Profil() throws IOException {
        this.loadUser();
    }
    public static boolean fileExist(){
        String filePath = "src/main/resources/ulb/database/user.txt";
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }
    public void loadBasicData(List<String> lines){
        if (lines.size() >= 3){
            this.lastname = lines.get(0);
            this.firstname = lines.get(1);
            this.sex = lines.get(2);
            String birthDate = lines.get(3);
            String[] birthData = birthDate.split("\\s+");
            // Check if the size of the list is 3 for day month and years
            if (birthData.length != 3) {
                throw new IllegalStateException("List size is not 3. Please delete the old user file.");
            }
            int birthDay = Integer.parseInt(birthData[0]);
            int birthMonth = Integer.parseInt(birthData[1]);
            int birthYear = Integer.parseInt(birthData[2]);
            this.birthDay = LocalDate.of(birthYear, birthMonth, birthDay);

        }else{
            throw new RuntimeException("Intentional crash: trying to load a database that does not exist");
        }
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSex() {
        return sex;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public Set<LocalDate> getAllWeightsDate(){
        return this.database.weights.keySet();
    }
    public float getWeight(LocalDate date){
        return this.database.getWeight(date);
    }
    public void addWeight(LocalDate date, float weight){
        this.database.addWeight(date, weight);
    }
    public void editWeight(LocalDate date, float newweight){
        this.database.editWeight(date, newweight);
    }
    public void removeWeight(LocalDate date){
        this.database.removeWeight(date);
    }
    public float getNewestWeight(){
        Set<LocalDate> dateset = this.getAllWeightsDate();
        LocalDate newestDate = null;
        for (LocalDate date : dateset) {
            if (newestDate == null || date.isAfter(newestDate)) {
                newestDate = date;
            }
        }
        return this.getWeight(newestDate);
    }
    public Set<LocalDate> getAllHeightsDate(){
        return this.database.heights.keySet();
    }
    public float getHeight(LocalDate date){
        return this.database.getHeight(date);
    }
    public void addHeight(LocalDate date, float height){
        this.database.addHeight(date, height);
    }
    public void editHeight(LocalDate date, float newheight){
        this.database.editHeight(date, newheight);
    }
    public void removeHeight(LocalDate date){
        this.database.removeHeight(date);
    }
    public float getNewestHeight(){
        Set<LocalDate> dateset = this.getAllHeightsDate();
        LocalDate newestDate = null;
        for (LocalDate date : dateset) {
            if (newestDate == null || date.isAfter(newestDate)) {
                newestDate = date;
            }
        }
        return this.getHeight(newestDate);
    }
    public void loadUser() throws IOException {
        if (fileExist()){
            String filePath = "src/main/resources/ulb/database/user.txt";
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (int i = 0; i < lines.size(); i++) {
                lines.set(i, lines.get(i).trim());
            }
            this.loadBasicData(lines);
            this.loadDatabase(lines);
        }
    }
    public void loadDatabase(List<String> lines) {
        //modifying the number next to > to change the line that Database start
        if (lines.size() > 4){
            for (int i=4; i<lines.size();i++){
                String data = lines.get(i);
                // load data in format:    weights:2 3 2024:50
                if (data.contains(":")) {
                    String type = data.substring(0, data.indexOf(":"));
                    data = data.substring(data.indexOf(":")+1);
                    if (data.contains(":")){
                        String date = data.substring(0, data.indexOf(":"));
                        String value = data.substring(data.indexOf(":")+1);
                        String[] datelist = date.split("\\s+");
                        LocalDate localDate = LocalDate.of(Integer.parseInt(datelist[2]), Integer.parseInt(datelist[1]), Integer.parseInt(datelist[0]));
                        if (type.equals("weights")){
                            database.weights.put(localDate, Float.parseFloat(value));
                        }else if (type.equals("heights")){
                            database.heights.put(localDate, Float.parseFloat(value));
                        }else{
                            throw new IllegalStateException("User file corrupted. Please delete the old user file.");
                        }
                    }else {
                        throw new IllegalStateException("User file corrupted. Please delete the old user file.");
                    }

                }else {
                    throw new IllegalStateException("User file corrupted. Please delete the old user file.");
                }
            }
        }
    }
    public  void saveBasicData(BufferedWriter writer) throws IOException {
        writer.write(this.lastname +"\n");
        writer.flush();
        writer.write(this.firstname+"\n");
        writer.flush();
        writer.write(this.sex+"\n");
        writer.flush();
        writer.write(this.birthDay.getDayOfMonth()+" "+ this.birthDay.getMonthValue() + " "+ this.birthDay.getYear()+"\n");
        writer.flush();
    }
    public void saveDatabase(BufferedWriter writer) throws IOException {
        // Convert the HashMap keys to a sorted TreeMap
        TreeMap<LocalDate, Float> sortedMap = new TreeMap<>(this.database.weights);
        // Iterate over the sorted entries
        for (Map.Entry<LocalDate, Float> entry : sortedMap.entrySet()) {
            LocalDate date= entry.getKey();
            // write the weights data in format:    weights:2 3 2024:50
            writer.write("weights:"+
                    date.getDayOfMonth()+" "+
                    date.getMonthValue() + " "+
                    date.getYear()+":"+entry.getValue()+"\n");
            writer.flush();
        }
        sortedMap = new TreeMap<>(this.database.heights);
        for (Map.Entry<LocalDate, Float> entry : sortedMap.entrySet()) {
            LocalDate date= entry.getKey();
            // write the weights data in format:    weights:2 3 2024:50
            writer.write("heights:"+
                    date.getDayOfMonth()+" "+
                    date.getMonthValue() + " "+
                    date.getYear()+":"+entry.getValue()+"\n");
            writer.flush();
        }
    }
    public void saveUser() throws IOException {
        String filePath = "src/main/resources/ulb/database/user.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        this.saveBasicData(writer);
        this.saveDatabase(writer);
    }
}
