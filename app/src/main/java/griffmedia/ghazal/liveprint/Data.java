package griffmedia.ghazal.liveprint;

import java.util.ArrayList;

/**
 * Contains the data of the app.
 *
 * @author Patrick Ghazal
 * @version 1.0
 */
public class Data {

    public static final int bufferSize = 8096;

    public static final String compLinksFileName = "CompaniesLinks.txt";

    public static final String bgColourBeforeM = "#EEEEEE";

    private ArrayList<Company> partnerComps;

    private static Data instance;

    private Data() {
        partnerComps = new ArrayList<Company>();
    }

    /**
     * Provides public access to the <code>instance</code> field. Creates it if null.
     *
     * @return the static <code>Data</code> instance
     */
    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public ArrayList<Company> getPartnerComps() {
        return partnerComps;
    }

    /**
     * Adds a company to the list of partners.
     *
     * @param c company to be added
     */
    public void addComp(Company c) {
        if (partnerComps == null)
            partnerComps = new ArrayList<Company>();
        partnerComps.add(c);

    }

    /**
     * Checks for the presence of a given company in the list of partners.
     *
     * @param c company to be searched
     * @return true if the company exists in the list of partners
     */
    public boolean hasComp(Company c) {
        for (Company existingComp : partnerComps) {
            if (existingComp.identical(c, true)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a list of companies in String format.
     *
     * @return a list of partner companies in string format
     * @see Company#toString()
     */
    public ArrayList<String> dataToString() {
        ArrayList<String> fullData = new ArrayList<String>();
        for (Company c : partnerComps) {
            fullData.add(c.toString());
        }
        return fullData;
    }

    /**
     * Looks for a partner company based on a given name.
     *
     * @param name <code>String</code> containing the name of the company to look for
     * @return the found <code>Company</code>, or <code>null</code>
     */
    public Company getCompByName(String name) {
        for (Company c : this.getPartnerComps()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Erases all data.
     *
     * @see Data#removeComps()
     * @see Company#removeLinks()
     */
    public void eraseAllData() {
        for (Company comp : this.getPartnerComps()) {
            comp.removeLinks();
        }
        removeComps();
    }

    /**
     * Removes all existing partner companies from the list.
     */
    private void removeComps() {
        while (this.getPartnerComps().size() > 0) {
            this.getPartnerComps().remove(0);
        }
    }

}

/**
 * Partner company of LivePrint. Provides links.
 */
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

    public ArrayList<Link> getLinks() {
        return this.links;
    }

    public void setPassword(String pw) {
        this.password = pw;
    }

    /**
     * Adds a link to <code>this Company></code>.
     *
     * @param l link to be added
     */
    public void addLink(Link l) {
        this.links.add(l);
    }

    /**
     * Checks whether the provided company is identical to <code>this Company</code>.
     *
     * @param c          Company to be compared
     * @param checkForPW boolean that specifies whether the passwords should also match
     * @return true if <code>this Company</code> and c are identical.
     */
    public boolean identical(Company c, boolean checkForPW) {
        boolean pwCheck = (checkForPW ? this.getPassword().equals(c.getPassword()) : true);
        return this.getName().equals(c.getName()) && pwCheck;
    }

    /**
     * Formats <code>this Company</code> and its data for saving purposes.
     *
     * @return the formatted company data
     */
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

    /**
     * Removes all links from <code>this Company</code>.
     */
    public void removeLinks() {
        while (this.getLinks().size() > 0) {
            this.getLinks().remove(0);
        }
    }

    /**
     * Checks for the existence of the given link and deletes it.
     *
     * @param toRemove link to be removed
     */
    public void removeLink(Link toRemove) {
        Link found = null;
        for (Link compLink : this.getLinks()) {
            if (compLink.identical(toRemove)) {
                found = compLink;
            }
        }
        if (found != null) {
            this.getLinks().remove(found);
        }
    }

}

/**
 * Pair of photo and video files that a company submitted to LivePrint.
 */
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

    /**
     * Formats <code>this Link</code> for saving purposes.
     *
     * @return String-formatted link
     */
    public String toString() {
        return this.getPhotoName() + "//" + this.getVideoName();
    }

    /**
     * Checks whether the provided link is identical to <code>this Link</code>.
     *
     * @param link link to be compared to <code>this Link</code>
     * @return true if the links are identical
     */
    public boolean identical(Link link) {
        return this.getVideoName().equals(link.getVideoName()) && this.getPhotoName().equals(link.getPhotoName());
    }

    public void setPhotoName(String newPhotoName) {
        this.photoName = newPhotoName;
    }

    public void setVideoName(String newVideoName) {
        this.videoName = newVideoName;
    }

    /**
     * Creates a duplicate link with the same data as <code>this Link</code>.
     *
     * @return the duplicate link
     */
    public Link duplicate() {
        Link dupl = new Link(this.getPhotoName(), this.getVideoName());
        return dupl;
    }

}