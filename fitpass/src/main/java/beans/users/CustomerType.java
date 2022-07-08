package beans.users;

public class CustomerType {
    private CustomerRankType rankType;
    private double discount;
    private double points;

    public CustomerType(CustomerRankType rankType, double discount, double points) {
        this.rankType = rankType;
        this.discount = discount;
        this.points = points;
    }

    public CustomerRankType getRankType() {
        return rankType;
    }

    public void setRankType(CustomerRankType rankType) {
        this.rankType = rankType;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
