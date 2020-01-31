public class Water extends Product {
    private float capacity;

    public Water(String name, float price, float capacity) {
        super(name, price);
        this.capacity=capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public float getCapacity() {
        return capacity;
    }
}
