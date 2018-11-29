/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;

public class OrderModel implements Serializable{
    private String tx_id;
    private String holder;
    private String shareName;
    private String quantity;
    private String price;
    private String timestamp;
    private Date date;

    public OrderModel() {
    }

    public OrderModel(String[] args) {
        this.tx_id = args[0];
        this.holder = args[2];
        this.shareName = args[4];
        this.quantity = args[6];
        this.price = args[8];
        this.timestamp = args[12];
        long time = Integer.parseInt(timestamp);
        time*=1000;
        this.date = new Date(time);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTx_id() {
        return tx_id;
    }

    public void setTx_id(String tx_id) {
        this.tx_id = tx_id;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
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

    @Override
    public String toString() {
        return "Model{" + "tx_id=" + tx_id + ", holder=" + holder + ", shareName=" + shareName + ", quantity=" + quantity + ", price=" + price + ", timestamp=" + timestamp + '}';
    }

}
