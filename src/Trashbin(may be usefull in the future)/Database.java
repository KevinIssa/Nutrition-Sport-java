package ulb.models;

import java.time.LocalDate;
import java.util.HashMap;

public class Database{
    HashMap<LocalDate, Float> weights = new HashMap<>();
    HashMap<LocalDate, Float> heights = new HashMap<>();
    public float getWeight(LocalDate date){
        if (this.weights.get(date) != null){
            return this.weights.get(date);
        }else{
            throw new IllegalStateException("date not in database");
        }
    }
    public void addWeight(LocalDate date, float weight){
        if (this.weights.get(date) == null){
            this.weights.put(date, weight);
        }else{
            throw new IllegalStateException("date in database, for editing date please use edit");
        }
    }
    public void editWeight(LocalDate date, float newweight){
        if (this.weights.get(date) != null){
            this.weights.put(date, newweight);
        }else{
            throw new IllegalStateException("date not in database, for new date please use add and not edit");
        }
    }
    public void removeWeight(LocalDate date){
        if (this.weights.get(date) != null){
            this.weights.remove(date);
        }else{
            throw new IllegalStateException("date not in database");
        }
    }
    public float getHeight(LocalDate date){
        if (this.heights.get(date) != null){
            return this.heights.get(date);
        }else{
            throw new IllegalStateException("date not in database");
        }
    }
    public void addHeight(LocalDate date, float height){
        if (this.heights.get(date) == null){
            this.heights.put(date, height);
        }else{
            throw new IllegalStateException("date in database, for editing date please use edit");
        }
    }
    public void editHeight(LocalDate date, float newheight){
        if (this.heights.get(date) != null){
            this.heights.put(date, newheight);
        }else{
            throw new IllegalStateException("date not in database, for new date please use add and not edit");
        }
    }
    public void removeHeight(LocalDate date){
        if (this.heights.get(date) != null){
            this.heights.remove(date);
        }else{
            throw new IllegalStateException("date not in database");
        }
    }

}
