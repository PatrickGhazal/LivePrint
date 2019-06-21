package griffmedia.ghazal.liveprint;

import android.content.Context;

import java.util.ArrayList;

public class Data {

    public static final int bufferSize = 8096;
    public static final String compLinksFileName = "CompaniesLinks.txt";
    public static final String bgColourBeforeM = "#EEEEEE";

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
            if (existingComp.identical(c, true)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> dataToString() {
        ArrayList<String> fullData = new ArrayList<String>();
        for (Company c : partnerComps) {
            fullData.add(c.toString());
        }
        return fullData;
    }

    public Company getCompByName(String name) {
        for (Company c : this.getPartnerComps()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public void eraseAllData() {
        for (Company comp : this.getPartnerComps()) {
            comp.removeLinks();
        }
        removeComps();
    }

    private void removeComps() {
        while (this.getPartnerComps().size() > 0) {
            this.getPartnerComps().remove(0);
        }
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

    public boolean identical(Company c, boolean checkForPW) {
        boolean pwCheck = (checkForPW ? this.getPassword().equals(c.getPassword()) : true);
        return this.getName().equals(c.getName()) && pwCheck;
    }

    public String toString() {
        String returned = "";
        if (this.getLinks().size() == 0) {
            returned = this.getName() + " :: " + this.getPassword() + " :: no_links";
        } else {
            returned = this.getName() + " :: " + this.getPassword() + " :: ";
            for (Link l : this.getLinks()) {
                returned += (l.toString() + "---");
            }
        }
        return returned;
    }

    public void removeLinks() {
        while (this.getLinks().size() > 0) {
            this.getLinks().remove(0);
        }
    }

    public void removeLink(Link toRemove) {
        for (Link compLink : this.getLinks()) {
            if (compLink.identical(toRemove)) {
                this.getLinks().remove(compLink);
            }
        }
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

    public boolean identical(Link link) {
        return this.getVideoName().equals(link.getVideoName()) && this.getPhotoName().equals(link.getPhotoName());
    }

    public void setPhotoName(String newPhotoName) {
        this.photoName = newPhotoName;
    }

    public void setVideoName(String newVideoName) {
        this.videoName = newVideoName;
    }

}
