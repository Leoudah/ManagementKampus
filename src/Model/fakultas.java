/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author 62895
 */
public class fakultas {
   private int fakultasID;
   private String name;
   private String code;
   private String description;

    public fakultas(int fakultasID, String name, String code, String description) {
        this.fakultasID = fakultasID;
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public int getFakultasId() { return fakultasID; }
    public void setFakultasId(int fakultasID) { this.fakultasID = fakultasID;}
    public String getFakultasName() { return name; }
    public void setFakultasName(String name) { this.name = name;}
    public String getFakultasCode() { return code; }
    public void setFakultasCode(String code) { this.code = code;}
    public String getFakultasDesc() { return description; }
    public void setFakultasDesc(String description) { this.description = description;}
    
}
