public class Milk extends Product {
    private float capacity;

    public Milk(String name, float price,float capacity) {
        super(name, price);
        this.capacity = capacity;
    }
    public void setCapacity(float capacity){
       this.capacity = capacity;
    }

    public float getCapacity() {
        return this.capacity;
    }
}
