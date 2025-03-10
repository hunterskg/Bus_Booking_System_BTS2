/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author FPT SHOP
 */
public class Booking {

    private String bcode;
    private String pcode;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Date odate;
    private int paid;
    private int seat;

    public Booking() {
    }

    public Booking(String bcode, String pcode, Date odate, int paid, int seat) {
        this.bcode = bcode;
        this.pcode = pcode;
        this.odate = odate;
        this.paid = paid;
        this.seat = seat;
    }

    public Booking(String bcode, String pcode, int paid, int seat) {
        this.bcode = bcode;
        this.pcode = pcode;
        this.odate = new Date();
        this.paid = paid;
        this.seat = seat;
    }

    public String getBcode() {
        return bcode;
    }

    public void setBcode(String bcode) {
        this.bcode = bcode;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Date getOdate() {
        return odate;
    }

    public void setOdate(Date odate) {
        this.odate = odate;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("Bus code: %s; Passenger code: %s; Date: %s; Paid: %d; Seat: %d",
                bcode, pcode, dateFormat.format(odate), paid, seat);
    }
    
}
