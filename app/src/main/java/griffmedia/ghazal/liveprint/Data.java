package griffmedia.ghazal.liveprint;

import java.util.ArrayList;

public class Data {

    private ArrayList<Company> partnerComps;
    private static Data instance;

    private Data() {
        partnerComps = new ArrayList<Company>();
    }

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public ArrayList<Company> getPartnerComps() {
        return partnerComps;
    }

    public void addComp(Company c) {
        if (partnerComps == null)
            partnerComps = new ArrayList<Company>();
        partnerComps.add(c);
    }

    public boolean hasComp(Company c) {
        for (Company existingComp : partnerComps) {
            if (existingComp.identical(c)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> dataToString() {
        ArrayList<String> fullData = new ArrayList<String>();
        for (Company c : partnerComps) {
            for (Link l : c.getLinks()) {
                String linkString = c.getName() + " :: " + l.toString();
                fullData.add(linkString);
            }
        }
        return fullData;
    }

}

class Company {

    private String name;
    private String password;
    private ArrayList<Link> links;

    public Company(String n) {
        this.name = n;
        this.password = "";
        links = new ArrayList<Link>();
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String pw) {
        this.password = pw;
    }

    public void addLink(Link l) {
        this.links.add(l);
    }

    public ArrayList<Link> getLinks() {
        return this.links;
    }

    public boolean identical(Company c) {
        return this.getName().equals(c.getName()) && this.getPassword().equals(c.getPassword());
    }

}

class Link {

    private String photoName;
    private String videoName;

    public Link(String pn, String vn) {
        this.photoName = pn;
        this.videoName = vn;
    }

    public String getPhotoName() {
        return this.photoName;
    }

    public String getVideoName() {
        return this.videoName;
    }

    public String toString() {
        return this.getPhotoName() + "//" + this.getVideoName();
    }

}
