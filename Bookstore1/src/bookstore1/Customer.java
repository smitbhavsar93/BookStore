
package bookstore1;


public class Customer {
    private String cusname;
    private String cuspass;
    private double point;

public Customer (){
    this.cusname = "";
    this.cuspass = "";
    this.point = 0;
}

public Customer(String cusname, String cuspass, double point){
    this.cusname = cusname;
    this.cuspass = cuspass;
    this.point = point;
    
}

public String getCusname(){
    return cusname;
}

public void setCusname(String cusname){
    this.cusname = cusname;
}

public String getCuspass(){
    return cuspass;
}

public void setCuspass(String cuspass){
    this.cuspass = cuspass;
}

public double getPoint(){
    return point;
}

public void setPoint(double point){
    this.point = point;
}


}
