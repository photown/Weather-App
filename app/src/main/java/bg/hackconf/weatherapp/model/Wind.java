package bg.hackconf.weatherapp.model;

import com.google.gson.annotations.Expose;

/**
 * By Antoan Angelov on 17-Sep-15.
 */
public class Wind {
    @Expose
    private Double speed;
    @Expose
    private Integer deg;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public void setDeg(Integer deg) {
        this.deg = deg;
    }

}
