package bookstore1;

import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.application.Application;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.geometry.Pos;

public class Bookstore1 extends Application {
    Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button10,button11,button12,button13,button14;
    Stage window;
    Scene manager,scenecustomer,login,pain,buysc;
    TableView<Product> table,table3;
    TableView<Customer> table2;
    TextField bookNameInput,bookPriceInput,customerNameInput,customerPassInput;
    Label cusLab;
    
    ObservableList<Product> globalProduct = FXCollections.observableArrayList();
    public String loginCustomerName = "";
    public double loginCustomerPoints;

    public int lastBookIndex = -5;
    
    public static void main(String[] args) {
        launch(args);
        
        
    }
    
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;                                 // one application window open
        primaryStage.setTitle("BookStore App");                //application title
             
        
         // login screen
        button1 = new Button();                              // create a button
        button1.setText("Login");                            // text on button
        GridPane.setConstraints(button1,1,4);
        
        //Layout
        GridPane layout = new GridPane();                   // Layout on the Scene
        layout.setPadding(new Insets(10,10,10,10));         // Border of 10 pixels around the screen
        layout.setVgap(8);                                  // space between colums
        layout.setHgap(5);                                 // space between rows
        
        //Labels
        Label nameLab = new Label("Username");              // text on the application (user cannot interact with it)
        GridPane.setConstraints(nameLab,0,2);               // Position of the text on the layout 
      
        Label passLab = new Label("Password");
        GridPane.setConstraints(passLab,0,3);
        
        Label text = new Label ("Welcome to the Bookstore App");
        GridPane.setConstraints(text,0,0);
        
        //Username field
        TextField nameInput = new TextField();
        nameInput.setPromptText("Username");
        GridPane.setConstraints(nameInput,1,2);
        
        //Password Field        
        TextField passInput = new TextField();
        passInput.setPromptText("Password"); 
        GridPane.setConstraints(passInput,1,3);    
        

        //Button functions         
        button1.setOnAction(e -> {
             String type =  nameInput.getText();
             String type2= passInput.getText(); 
             ArrayList<String> listOfCustomers = ReadCustomers();

             if (type.equals("admin")){
                 if (type2.equals("admin")){
                     window.setScene(manager);
                 }
             } else {
                 for (int i = 0; i < listOfCustomers.size(); i++) {                //Goes through the books arraylist
                     String[] arr = listOfCustomers.get(i).split(" ");        //Splits each line into words
                     String name = arr[0];
                     String password = arr[1];
                     
                     if (type.equals(name)) {
                         if (type2.equals(password)) {
                             loginCustomerName = type; 
                             loginCustomerPoints = Double.valueOf(arr[2]);   
                             Label oop = new Label("Welcome "+loginCustomerName);
                             window.setScene(pain);
                             break;
                        }
                    }
                }
            }
     
        });
        
        layout.getChildren().addAll(button1,nameInput,passInput,nameLab,passLab,text);        
        login = new Scene(layout, 650, 275);                
        
         //Buy screen
         button14 = new Button();
         button14.setText("Logout");
         button14.setOnAction(e -> window.setScene(login));
         GridPane.setConstraints(button14,3,2);
         
         Label buy1 = new Label("The total cost is " + Buy());
         GridPane.setConstraints(buy1,3,0);
         Label buy2 = new Label("Your total points is" /*+Buy.loginCustomerPoints*/);
         GridPane.setConstraints(buy2,3,1);
        
        GridPane layout3 = new GridPane();                   
        layout3.setPadding(new Insets(10,10,10,10));         
        layout3.setVgap(8);                                 
        layout3.setHgap(5);             
        layout3.getChildren().addAll(buy1,buy2,button14);
        buysc = new Scene(layout3,200,125);
       
        
        //Manager-Books scene
         
        //Name column 
        TableColumn<Product,String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(290);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Price column 
        TableColumn<Product,Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(290);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        table = new TableView<>();
        table.setItems(getProduct());
        table.getColumns().addAll(nameColumn,priceColumn);
       
         //Textfields 
       
        bookNameInput = new TextField();
        bookNameInput.setPromptText("Name");
        bookNameInput.setMinWidth(150);
        
        bookPriceInput = new TextField();
        bookPriceInput.setPromptText("Price");
        bookPriceInput.setMinWidth(150);
        
        button5 = new Button();
        button5.setText("Add");
        button5.setOnAction(e -> addButtonClicked());
        
        button6 = new Button();
        button6.setText("Delete");
        button6.setOnAction(e -> deleteButtonClicked());
        
        button7 = new Button();
        button7.setText("Back");
        button7.setOnAction(e -> window.setScene(manager));
        
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(15);  
        hBox.getChildren().addAll(bookNameInput,bookPriceInput,button5,button6,button7);
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(table,hBox);
        Scene books;
        books = new Scene(vBox);
       
        //Customer Login screen
        TableColumn<Product,String> naColumn = new TableColumn<>("Name");
        naColumn.setMinWidth(230);
        naColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        
        TableColumn<Product,Double> priColumn = new TableColumn<>("Price");
        priColumn.setMinWidth(230);
        priColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        TableColumn<Product,CheckBox> selColumn = new TableColumn<>("Select");
        selColumn.setMinWidth(100);
        selColumn.setCellValueFactory(new PropertyValueFactory<>("select"));
        
        table3= new TableView<>();
        table3.setItems(globalProduct);
        table3.getColumns().addAll(naColumn,priColumn,selColumn);
        
       
        cusLab = new Label("Welcome" + loginCustomerName); 
        System.out.println("Customer name: " + loginCustomerName);
        
        button11 = new Button();
        button11.setText("Buy");
        button11.setOnAction(e -> Buy());
        button11.setOnAction(e -> window.setScene(buysc));
        
        button12 = new Button();
        button12.setText("Redeem points and Buy");
        button12.setOnAction(e -> PointBuy()
         //window.setScene(buysc));
        );
        
        button13 = new Button();
        button13.setText("Logout");
        button13.setOnAction (e -> window.setScene(login));
        
        HBox cuslab = new HBox();
        cuslab.setAlignment(Pos.CENTER);
        cuslab.setPadding(new Insets(2,2,2,2));
        cuslab.getChildren().add(cusLab);
        
        
        HBox hhhBox = new HBox();
        hhhBox.setPadding(new Insets(10,10,10,100));
        hhhBox.setSpacing(30);  
        hhhBox.getChildren().addAll(button11,button12,button13);
        
        VBox vvvBox = new VBox();
        vvvBox.getChildren().addAll(cuslab,table3,hhhBox);
        pain = new Scene(vvvBox);
         
        // Manager-Customer screen
        TableColumn<Customer,String> cusnameColumn = new TableColumn<>("Name");
        cusnameColumn.setMinWidth(250);
        cusnameColumn.setCellValueFactory(new PropertyValueFactory<>("cusname"));
         
        TableColumn<Customer,String> cuspassColumn = new TableColumn<>("Password");
        cuspassColumn.setMinWidth(220);
        cuspassColumn.setCellValueFactory(new PropertyValueFactory<>("cuspass"));
        
        TableColumn<Customer,Double> pointColumn = new TableColumn<>("Point");
        pointColumn.setMinWidth(145);
        pointColumn.setCellValueFactory(new PropertyValueFactory<>("point"));
      
        table2 = new TableView<>();
        table2.setItems(getCustomer());
        table2.getColumns().addAll(cusnameColumn,cuspassColumn,pointColumn);
       
        button9 = new Button();
        button9.setText("Add");
        button9.setOnAction(e -> addButtonClickedCustomer());
        
        button10 = new Button();
        button10.setText("Delete");
        button10.setOnAction(e -> deleteButtonClickedCustomer());
        
        button8 = new Button();
        button8.setText("Back");
        button8.setOnAction(e -> window.setScene(manager));
        
        //Text fields
        customerNameInput = new TextField();
        customerNameInput.setPromptText("Name");
        customerNameInput.setMinWidth(150);
        
        customerPassInput = new TextField();
        customerPassInput.setPromptText("Password");
        customerPassInput.setMinWidth(150);
      
        HBox hhBox = new HBox();
        hhBox.setPadding(new Insets(10,10,10,10));
        hhBox.setSpacing(15);  
        hhBox.getChildren().addAll(customerNameInput,customerPassInput,button9,button10,button8);
        
        VBox vvBox = new VBox();
        vvBox.getChildren().addAll(table2,hhBox);
        Scene customer;
        customer = new Scene(vvBox);
        
        //Manager screen 
        button2 = new Button();                             
        button2.setText("Books");                    
        button2.setOnAction(e ->window.setScene(books));
        GridPane.setConstraints(button2,6,3);
        
        button3 = new Button();
        button3.setText("Customer");
        button3.setOnAction (e -> window.setScene(customer));  
        GridPane.setConstraints(button3,6,5);
        
        button4 = new Button();
        button4.setText("Logout");
        button4.setOnAction(e -> window.setScene(login));
        GridPane.setConstraints(button4,6,7);
        

        //Layout
        GridPane layout2 = new GridPane();                                     // position on the scene
        layout2.setPadding(new Insets(10,10,10,10));
        layout2.setVgap(8); 
        layout2.setHgap(35); 
        layout2.getChildren().addAll(button2,button3,button4);                 // add button to the scene
        manager = new Scene(layout2, 550, 250);                                // window size

       
        
         window.setScene(login);                // load login scene
         window.show();     
        
 }

    public Double Buy(){
        double cartTotal = 0;
        double totalPointsEarned = 0;
        for(int i=0; i < globalProduct.size(); i++){
            if(globalProduct.get(i).getSelect().isSelected()){
                cartTotal += globalProduct.get(i).getPrice();
            }
        }
        System.out.println("Total cart: " + cartTotal);
        totalPointsEarned = cartTotal * 10;
        System.out.println("Point of the customer: " + loginCustomerPoints);
        System.out.println("Total Points earned: " + totalPointsEarned);
        loginCustomerPoints += ((int) totalPointsEarned);
        System.out.println("Total points of the customer after buy: " + loginCustomerPoints);
        changeCustomerPoints(loginCustomerPoints);
        //deleteActionAfterBuy();
        
        // cartTotal returns
        // loginCustomer points are updated here
        // customers name is --->   loginCustomerName
        // if the total points are more than 1000 you put Gold else Silver as in membership status
        // 
        
        return cartTotal;
    }
    
    
    
    public double PointBuy(){
        System.out.println("Buy points clicked");
        System.out.println("Customer name: " + loginCustomerName);
        
        double customerUsablePointsInCAD = loginCustomerPoints / 100;
        int customerUsablePointsInCADInInteger = (int) customerUsablePointsInCAD;
        double cartTotal = 0;
        
        for(int i=0; i < globalProduct.size(); i++){
            if(globalProduct.get(i).getSelect().isSelected()){
                cartTotal += globalProduct.get(i).getPrice();
            }
        }
        
        System.out.println("Total: " + cartTotal);
        System.out.println("Point of the customer: " + loginCustomerPoints);
        
        // customers points is -> loginCustomerPoints
        // cart total returns from this function
        
        if(customerUsablePointsInCADInInteger >= cartTotal){
            System.out.println("Buyable amount in CAD with points: " + customerUsablePointsInCADInInteger);
            
            loginCustomerPoints = loginCustomerPoints - (cartTotal * 100);
            changeCustomerPoints(loginCustomerPoints);
            System.out.println("Price after points redeemed: " + 0);
            System.out.println("New points of the customer: " + loginCustomerPoints);
            //deleteActionAfterBuy();
            return 0;
        } else{
            System.out.println("Buyable amount with points: " + customerUsablePointsInCADInInteger);
            System.out.println("Price after points redeemed: " + (cartTotal - customerUsablePointsInCADInInteger));
            loginCustomerPoints = loginCustomerPoints - (customerUsablePointsInCADInInteger * 100);
            changeCustomerPoints(loginCustomerPoints);
            System.out.println("New points of the customer: " + loginCustomerPoints);
           // deleteActionAfterBuy();
            return (cartTotal - customerUsablePointsInCADInInteger);
        }
    }
    
    public void changeCustomerPoints(double pointsToChange){
        String changedArray = "";
        ArrayList<String> listOfCustomers = ReadCustomers();                //Reads the Books.txt 
        for(int i = 0; i < listOfCustomers.size(); i++){                //Goes through the whole list and finds which book you want to delete in the file
            String[] arr = listOfCustomers.get(i).split(" ");
            
            if(loginCustomerName.equals(arr[0])){
                String pointsInString = "" + pointsToChange;
                arr[2] = pointsInString;
                changedArray = arr[0];
                for(int j = 1; j < arr.length; j++){
                    changedArray += " " + arr[j];
                }
                listOfCustomers.set(i, changedArray);
            } 
        }
        
        FileWriter writer = null;                                   
        try {
            writer = new FileWriter("Customers.txt");                   //In the next 15 lines deletes everything in the Books.txt file
        } catch (IOException ex) {
            System.out.println("Couldn't create Books2");;
        }
            
        try {
            writer.write("");
        } catch (IOException ex) {
            System.out.println("couldn't delete customers");;
        }
        try {
            writer.close();
        } catch (IOException ex) {
            System.out.println("Couldn't close customers");;
        }

        try {                                                       //Rewrites the list to th file
            writer = new FileWriter("Customers.txt");
        } catch (IOException ex) {
            System.out.println("Couldn't create Customers");;
        }
        try {
            if(listOfCustomers.size() > 0)
                writer.write("" + listOfCustomers.get(0));
        } catch (IOException ex) {
            System.out.println("IOException");
        }
        for(int i = 1; i < listOfCustomers.size(); i++) {
            try {
                writer.write("\n" + listOfCustomers.get(i));
            } catch (IOException ex) {
                System.out.println("IOException");
            }
        }
        try {
            writer.close();
        } catch (IOException ex) {
            System.out.println("Couldn't close books");;
        }
    }
    
    
    public static ArrayList<String> ReadCustomers(){
        ArrayList<String> listOfCustomers = new ArrayList<>();
        BufferedReader bufReader = null; 
        try {
            bufReader = new BufferedReader(new FileReader("Customers.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println("Problem with BufferReader");
        }
        
        String line = null; 
        try {
            line = bufReader.readLine();
        } catch (IOException ex) {
            System.out.println("Problem with readLine");
        }
        while (line != null) { 
            listOfCustomers.add(line); 
            try { 
                line = bufReader.readLine();
            } catch (IOException ex) {
                System.out.println("Problem with bufReader");
            }
        } 
        try { 
            bufReader.close();
        } catch (IOException ex) {
            System.out.println("Books not closed");
        }
        return listOfCustomers;
    }
    
    public static ArrayList<String> ReadBooks(){
        ArrayList<String> listOfBooks = new ArrayList<>();
        BufferedReader bufReader = null; 
        try {
            bufReader = new BufferedReader(new FileReader("Books.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println("Problem with BufferReader");
        }
        
        String line = null; 
        try {
            line = bufReader.readLine();
        } catch (IOException ex) {
            System.out.println("Problem with readLine");
        }
        while (line != null) { 
            listOfBooks.add(line); 
            try { 
                line = bufReader.readLine();
            } catch (IOException ex) {
                System.out.println("Problem with bufReader");
            }
        } 
        try { 
            bufReader.close();
        } catch (IOException ex) {
            System.out.println("Books not closed");
        }
        return listOfBooks;
    }
    
    public static void SaveCustomers(String thing){         //This function adds the books created to the Books.txt file
        
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream("Customers.txt", true);          //Open the file
            
            byte[] s_array = thing.getBytes();                      //Turn the string into bytes
            fos.write(s_array);                                     //Write the bytes

        } catch (FileNotFoundException ex) {                        //Throws file not found exception
            System.out.println("Customers not found");
        } catch (IOException ex) {
            System.out.println("IOException on SaveBooks");         //Throws IO exception
        }
        finally{
            try {
                fos.close();                                        //Close the file
            } catch (IOException ex) {                              //Throws couldn't close file exception
                System.out.println("Customers not closed");

            }
        }
    }
    
    
    public static void SaveBooks(String thing){         //This function adds the books created to the Books.txt file
        
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream("Books.txt", true);          //Open the file
            
            byte[] s_array = thing.getBytes();                      //Turn the string into bytes
            fos.write(s_array);                                     //Write the bytes

        } catch (FileNotFoundException ex) {                        //Throws file not found exception
            System.out.println("Books not found");
        } catch (IOException ex) {
            System.out.println("IOException on SaveBooks");         //Throws IO exception
        }
        finally{
            try {
                fos.close();                                        //Close the file
            } catch (IOException ex) {                              //Throws couldn't close file exception
                System.out.println("Books not closed");

            }
        }
    }
    
    
    public ObservableList<Customer> getCustomer(){
        ObservableList<Customer> customers = FXCollections.<Customer>observableArrayList();
        ArrayList<String> listOfCustomers = ReadCustomers();
        
        for(int i = 0; i < listOfCustomers.size(); i++){                //Goes through the books arraylist
            String[] arr = listOfCustomers.get(i).split(" ");        //Splits each line into words
            String name = "";
            String password = arr[arr.length-2];
            double points = Double.valueOf(arr[arr.length-1]);         //Last word is the price
            for(int j = 0; j < arr.length - 2; j++){                
                name += arr[j] + " ";                               //Add all the words except the price into a single string
            }
            customers.add(new Customer(name,password,points));                  //Creates a new object from the read book
        }

        return customers; 
    }

    public ObservableList<Product> getProduct(){                                     // can be used to read from a file
        ObservableList<Product> products = FXCollections.observableArrayList(); 
        ArrayList<String> listOfBooks = ReadBooks();                //Creates an Arraylist of the books in the Books.txt
        globalProduct.clear();
        for(int i = 0; i < listOfBooks.size(); i++){                //Goes through the books arraylist
            String[] arr = listOfBooks.get(i).split(" ");        //Splits each line into words
            int id = Integer.valueOf(arr[0]);
            String name = "";
            double price = Double.valueOf(arr[arr.length-1]);         //Last word is the price
            for(int j = 1; j < arr.length - 1; j++){                
                name += arr[j] + " ";                               //Add all the words except the price into a single string
            }
            products.add(new Product(id,name,price));                  //Creates a new object from the read book
            
        }
        if(products.size() > 0)
            lastBookIndex = products.get(products.size()-1).getId();
        else
            lastBookIndex = 0;
        
        globalProduct = products;
        return products;
     }
     
    public void addButtonClickedCustomer (){                    // To add a customer object
     Customer customer = new Customer();
     customer.setCusname(customerNameInput.getText());
     customer.setCuspass(customerPassInput.getText());

     table2.getItems().add(customer);
     String prefix = globalProduct.size() > 0 ? "\n" : "";
     String customerToSave = prefix + customerNameInput.getText() + " " + customerPassInput.getText() + " 0";     //Makes a whole line of the name of the book and its price
     SaveCustomers(customerToSave);                                    //Sends the created line to SaveBooks function 
     
     customerNameInput.clear();
     customerPassInput.clear();
    }

    public void deleteButtonClickedCustomer () {                 // To delete a customer from the object
      ObservableList<Customer> cusSelected, allCustomer;
      allCustomer = table2.getItems();
      cusSelected = table2.getSelectionModel().getSelectedItems();

      int wantToDelete= -9;
      ArrayList<String> lisOfCustomers = ReadCustomers();                //Reads the Books.txt 
      for(int i = 0; i < lisOfCustomers.size(); i++){                //Goes through the whole list and finds which book you want to delete in the file
          String[] arr = lisOfCustomers.get(i).split(" ");
          String name = "";
          String password = arr[arr.length-2];
          double points = Double.valueOf(arr[arr.length-1]);

          for(int j = 0; j < arr.length - 2; j++){
              name += arr[j] + " ";
          }
          if(name.equals(cusSelected.get(0).getCusname())){
              wantToDelete = i;
          }
      }
      if(wantToDelete != -9){    
          lisOfCustomers.remove(wantToDelete);                           //Deletes the book from the list
      } else{
          lisOfCustomers.remove(lisOfCustomers.size()-1);
      }   
      FileWriter writer = null;                                   
      try {
          writer = new FileWriter("Customers.txt");                   //In the next 15 lines deletes everything in the Books.txt file
      } catch (IOException ex) {
          System.out.println("Couldn't create Customers");;
      }

      try {
          writer.write("");
      } catch (IOException ex) {
          System.out.println("couldn't delete Customers");;
      }
      try {
          writer.close();
      } catch (IOException ex) {
          System.out.println("Couldn't close Customers.txt");;
      }

      try {                                                       //Rewrites the list to th file
          writer = new FileWriter("Customers.txt");
      } catch (IOException ex) {
          System.out.println("Couldn't create Customers");;
      }
      try {
          writer.write("" + lisOfCustomers.get(0));
      } catch (IOException ex) {
          System.out.println("IOException");
      }
      for(int i = 1; i < lisOfCustomers.size(); i++) {
          try {
              writer.write("\n" + lisOfCustomers.get(i));
          } catch (IOException ex) {
              System.out.println("IOException");
          }
      }
      try {
          writer.close();
      } catch (IOException ex) {
          System.out.println("Couldn't close Customers.txt");;
      }

      cusSelected.forEach(allCustomer::remove);
    }
     
    public void addButtonClicked(){
         Product products = new Product();
         products.setId(lastBookIndex += 1);
         products.setName(bookNameInput.getText());
         products.setPrice(Double.parseDouble(bookPriceInput.getText()));
         
         String prefix = globalProduct.size() > 0 ? "\n" : "";
         globalProduct.add(products);
         String bookToSave = prefix + (lastBookIndex) + " " +  bookNameInput.getText() + " " + bookPriceInput.getText();     //Makes a whole line of the name of the book and its price
         SaveBooks(bookToSave);                                    //Sends the created line to SaveBooks function
         
         bookNameInput.clear();
         bookPriceInput.clear();
     }
    
    public void deleteActionAfterBuy(){
        ObservableList<Product> remainingBooks = globalProduct;
        for(int i = globalProduct.size() - 1 ; i >= 0 ; i--){
            if(globalProduct.get(i).getSelect().isSelected()){
                deleteProductFromFile(globalProduct.get(i).getId());
                remainingBooks.remove(i);
            }
        }
        globalProduct = remainingBooks;
    }
    
    public void deleteProductFromFile(int id){
        int wantToDelete = -80;
        ArrayList<String> listOfBooks = ReadBooks();                //Reads the Books.txt 
        for(int i = 0; i < listOfBooks.size(); i++){                //Goes through the whole list and finds which book you want to delete in the file
            String[] arr = listOfBooks.get(i).split(" ");
            
            if(id == Integer.valueOf(arr[0])){
                wantToDelete = i;
            }
            
        }
        if(wantToDelete != -80){    
            listOfBooks.remove(wantToDelete);                           //Deletes the book from the list
        }   
        
        FileWriter writer = null;                                   
        try {
            writer = new FileWriter("Books.txt");                   //In the next 15 lines deletes everything in the Books.txt file
        } catch (IOException ex) {
            System.out.println("Couldn't create Books2");;
        }
            
        try {
            writer.write("");
        } catch (IOException ex) {
            System.out.println("couldn't delete books");;
        }
        try {
            writer.close();
        } catch (IOException ex) {
            System.out.println("Couldn't close books");;
        }

        try {                                                       //Rewrites the list to th file
            writer = new FileWriter("Books.txt");
        } catch (IOException ex) {
            System.out.println("Couldn't create Books");;
        }
        try {
            if(listOfBooks.size() > 0)
                writer.write("" + listOfBooks.get(0));
        } catch (IOException ex) {
            System.out.println("IOException");
        }
        for(int i = 1; i < listOfBooks.size(); i++) {
            try {
                writer.write("\n" + listOfBooks.get(i));
            } catch (IOException ex) {
                System.out.println("IOException");
            }
        }
        try {
            writer.close();
        } catch (IOException ex) {
            System.out.println("Couldn't close books");;
        }
    }
    
    public void deleteButtonClicked(){
        ObservableList<Product> productSelected,allProducts; 
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        
        int wantToDelete = -80;
        ArrayList<String> listOfBooks = ReadBooks();                //Reads the Books.txt 
        for(int i = 0; i < listOfBooks.size(); i++){                //Goes through the whole list and finds which book you want to delete in the file
            String[] arr = listOfBooks.get(i).split(" ");
            String name = "";
           
            for(int j = 1; j < arr.length - 1; j++){
                name += arr[j] + " ";
            }
            if(name.contains(productSelected.get(0).getName())){
                wantToDelete = i;
            }
        }
        if(wantToDelete != -80){    
            listOfBooks.remove(wantToDelete);                           //Deletes the book from the list
        }   
        
        FileWriter writer = null;                                   
        try {
            writer = new FileWriter("Books.txt");                   //In the next 15 lines deletes everything in the Books.txt file
        } catch (IOException ex) {
            System.out.println("Couldn't create Books2");;
        }
            
        try {
            writer.write("");
        } catch (IOException ex) {
            System.out.println("couldn't delete books");;
        }
        try {
            writer.close();
        } catch (IOException ex) {
            System.out.println("Couldn't close books");;
        }

        try {                                                       //Rewrites the list to th file
            writer = new FileWriter("Books.txt");
        } catch (IOException ex) {
            System.out.println("Couldn't create Books");;
        }
        try {
            if(listOfBooks.size() > 0){
                writer.write("" + listOfBooks.get(0));
            }
        } catch (IOException ex) {
            System.out.println("IOException");
        }
        for(int i = 1; i < listOfBooks.size(); i++) {
            try {
                writer.write("\n" + listOfBooks.get(i));
            } catch (IOException ex) {
                System.out.println("IOException");
            }
        }
        try {
            writer.close();
        } catch (IOException ex) {
            System.out.println("Couldn't close books");;
        }
        
        productSelected.forEach(allProducts::remove);
     }
 }