public class Bread extends Product{
    private float weight;


    public Bread(String name, float price, float weight) {
        super(name, price);
        this.weight = weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }
}
