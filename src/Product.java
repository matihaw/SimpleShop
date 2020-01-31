public abstract class Product {
    public float price;
    public String name;

    public Product(String name,float price){
        this.name=name;
        this.price=price;
    }

    public void setPrice (int price){
        this.price = price;
    }

    public float getPrice(){
        return this.price;
    }

    public void setName(String name){
        this.name =  name;
    }
    public String getName(){
        return this.name;
    }
}
