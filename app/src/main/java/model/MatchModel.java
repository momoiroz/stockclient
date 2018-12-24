/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author kamis
 */
public class MatchModel implements Serializable{
    private String buyTx_id;
    private String sellTx_id;
    private String buyer;
    private String seller;
    private String shareName;
    private String quantity;
    private String price;
    private String timestamp;
    private Date date;

    public MatchModel() {
    }

    public MatchModel(String[] args) {
        this.buyTx_id = args[2];
        this.sellTx_id = args[4];
        this.buyer = args[6];
        this.seller = args[8];
        this.shareName = args[10];
        this.quantity = args[12];
        this.price = args[14];
        this.timestamp = args[18];
        long time = Integer.parseInt(timestamp);
        time*=1000;
        this.date = new Date(time);
    }

    public String getBuyTx_id() {
        return buyTx_id;
    }

    public void setBuyTx_id(String buyTx_id) {
        this.buyTx_id = buyTx_id;
    }

    public String getSellTx_id() {
        return sellTx_id;
    }

    public void setSellTx_id(String sellTx_id) {
        this.sellTx_id = sellTx_id;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        String s;
        s="Share: "+ shareName+". Quantity: "+quantity+". Price: "+price+". Time: "+date.toString();
        return s;
    }
}
