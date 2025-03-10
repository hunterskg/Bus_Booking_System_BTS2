/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

/**
 *
 * @author FPT SHOP
 */
public class Bus {

    private String bcode;
    private String bnum;
    private String dstation;
    private String astation;
    private double dtime;
    private int seat;
    private int booked;
    private double atime;

    public String getBcode() {
        return bcode;
    }

    public void setBcode(String bcode) {
        this.bcode = bcode;
    }

    public String getBnum() {
        return bnum;
    }

    public void setBnum(String bnum) {
        this.bnum = bnum;
    }

    public String getDstation() {
        return dstation;
    }

    public void setDstation(String dstation) {
        this.dstation = dstation;
    }

    public String getAstation() {
        return astation;
    }

    public void setAstation(String astation) {
        this.astation = astation;
    }

    public double getDtime() {
        return dtime;
    }

    public void setDtime(double dtime) {
        this.dtime = dtime;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

    public double getAtime() {
        return atime;
    }

    public void setAtime(double atime) {
        this.atime = atime;
    }

    public Bus(String bcode, String bnum, String dstation, String astation, double dtime, int seat, int booked, double atime) {
        this.bcode = bcode;
        this.bnum = bnum;
        this.dstation = dstation;
        this.astation = astation;
        this.dtime = dtime;
        this.seat = seat;
        this.booked = booked;
        this.atime = atime;
    }

    public Bus() {
    }

    @Override
    public String toString() {
        return String.format("Bus code: %s; Bus number: %s; dstation: %s; astation: %s; dtime: %.1f; seat: %d; booked: %d; atime: %.1f",
                bcode, bnum, dstation, astation, dtime, seat, booked, atime);
    }

}
