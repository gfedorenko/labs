public class TTC {
    int range;
    String optics;
    String collar;
    int sightingRange;

    public void setRange(int range, String type) {
        if ((type == "gun" && range < 500) || type == "rifle") {
        this.range = range; }
        else {
            this.optics = "undefined";
        }
    }

    public void setOptics(String optics, String type) {
        if ((type == "gun" && optics == "false") || type == "rifle") {
            this.optics = optics; }
        else {
            this.optics = "undefined";
        }
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
