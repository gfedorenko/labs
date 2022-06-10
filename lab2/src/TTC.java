public class TTC {
    int range;
    String optics;
    String collar;
    int sightingRange;

    public void setRange(int range, String type) {
        if ((type.equals("gun") && range < 500) || type.equals("riffle"))
        this.range = range;
    }

    public void setOptics(String optics, String type) {
        if ((type.equals("gun") && optics.equals("false")) || (type.equals("riffle")) )
            this.optics = optics;
           }

    public void setCollar(String collar) {
        this.collar = collar;
    }

    public void setSightingRange(int sightingRange) {
        this.sightingRange = sightingRange;
    }

    public String getOptics() {
        return optics;
    }

    public int getRange() {
        return range;
    }

    public String getCollar() {
        return collar;
    }

    public int getSightingRange() {
        return sightingRange;
    }

    @Override
    public String toString() {
        return " optics:" + this.optics + " range: " + this.range + " collar: " + this.collar + " sighting range: " + this.sightingRange;

    }
}
