

package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HolderQueryResponseModel implements Serializable {

    @SerializedName("gem")
    @Expose
    private String gem;
    @SerializedName("VCB")
    @Expose
    private String vCB;
    @SerializedName("MWG")
    @Expose
    private String mWG;
    @SerializedName("VNM")
    @Expose
    private String vNM;

    public HolderQueryResponseModel(String gem, String vCB, String mWG, String vNM) {
        this.gem = gem;
        this.vCB = vCB;
        this.mWG = mWG;
        this.vNM = vNM;
    }

    public String getGem() {
        return gem;
    }

    public void setGem(String gem) {
        this.gem = gem;
    }

    public String getVCB() {
        return vCB;
    }

    public void setVCB(String vCB) {
        this.vCB = vCB;
    }

    public String getMWG() {
        return mWG;
    }

    public void setMWG(String mWG) {
        this.mWG = mWG;
    }

    public String getVNM() {
        return vNM;
    }

    public void setVNM(String vNM) {
        this.vNM = vNM;
    }

}
