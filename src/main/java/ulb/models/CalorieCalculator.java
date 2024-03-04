package ulb.models;

public class CalorieCalculator {
    Sport sport;
    int intensity_level;
    float weight;
    double time;

    public CalorieCalculator (Sport sport, int intensity_level, float weight, double time) {
        this.sport = sport;
        this.intensity_level = intensity_level;
        this.weight = weight;
        this.time = time;

    }

    public double getMET(){
        switch (sport) {
            case VOLLEYBALL:
                switch (intensity_level){
                    case 1 :
                        return Constants.MET_SLOW_VOLLEYBALL;
                    case 2 :
                        return  Constants.MET_AVERAGE_VOLLEYBALL;
                    case 3 :
                        return Constants.MET_INTENSE_VOLLEYBALL;

                }
            case WALKING:
                switch (intensity_level){
                    case 1 :
                        return Constants.MET_SLOW_WALK;
                    case 2 :
                        return Constants.MET_MODERATE_WALK;
                    case 3 :
                        return Constants.MET_FAST_WALK;

                }
            case SWIMMING:
                switch (intensity_level){
                    case 1 :
                        return Constants.MET_SLOW_SWIM;
                    case 2 :
                        return Constants.MET_MODERATE_SWIM;
                    case 3 :
                        return Constants.MET_FAST_SWIM;

                }
            case BIKING:
                switch (intensity_level){
                    case 1 :
                        return Constants.MET_SLOW_BIKE;
                    case 2 :
                        return Constants.MET_MODERATE_BIKE;
                    case 3 :
                        return Constants.MET_FAST_BIKE;

                }
            case RUNNING:
                switch (intensity_level){
                    case 1 :
                        return Constants.MET_SLOW_RUN;
                    case 2 :
                        return Constants.MET_MODERATE_RUN;
                    case 3 :
                        return Constants.MET_FAST_RUN;

                }
        }
        return 0;
    }
    public double compute() {
        double MET = getMET();
        return ((MET*3.5* this.weight)/200)* time;
    }

}
