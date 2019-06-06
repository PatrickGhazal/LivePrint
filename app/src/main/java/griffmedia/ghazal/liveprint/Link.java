package griffmedia.ghazal.liveprint;

public class Link {

    private String photoName;
    private String videoName;
    private String company;

    public Link(String p, String v, String c) {
        photoName = p;
        videoName = v;
        company = c;
    }

    public String getPhotoName() {
        return photoName;
    }

    public String getVideoName() {
        return videoName;
    }

    public String getCompany() {
        return company;
    }

    public String toString() {
        return photoName + "/" + videoName + "\n";
    }
}
