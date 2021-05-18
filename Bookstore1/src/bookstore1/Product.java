
package bookstore1;
import javafx.scene.control.CheckBox;

public class Product  {
    private int id;
    private String name;
    private double price;
    private CheckBox select;
    
    public Product (){
        this.id = -1;
        this.name = "";
        this.price = 0; 
        this.select = new CheckBox();
    }
    
    public Product(int id, String name, double price){
        this.id = id;
        this.name =  name;
        this.price = price;  
        this.select = new CheckBox();
    }
    
    public String getName(){
       return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public double getPrice(){
        return price;
    }
    
    public void setPrice(double price){
        this.price = price;
    }
    
     public CheckBox getSelect(){
        return select;
    }
    
    public void setSelect(CheckBox select){
        this.select = select; 
    }
    
}
