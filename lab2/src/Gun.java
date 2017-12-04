public class Gun {
    String type;
    String model;
    String handy;
    String origin;
    TTC    ttc;


    public void setType(String type) {
        this.type = type;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setHandy(String handy) {
        this.handy = handy;
    }

    public void setTtc(TTC ttc) {
       // System.out.println(ttc);
        this.ttc = ttc;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "type: " + this.type + " model: " + this.model + " handy: " + this.handy + " origin: "+ this.origin +
                " TTC:\n" + " optics:" + this.ttc.getOptics() + " range: " + this.ttc.getRange() + " collar: " + this.ttc.getCollar() + " sighting range: " + this.ttc.getSightingRange();
    }
}
