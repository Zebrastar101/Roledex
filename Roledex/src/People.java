public class People {
    private String first;
    private String last;
    private String phone;
    private String address;

    public People(String first, String last, String phone, String address) {
        this.first = first;
        this.last = last;
        this.phone = phone;
        this.address = address;
    }

    public String getFirst() {

        return first;
    }

    public String getLast() {

        return last;
    }

    public String getPhone() {

        return phone;
    }

    public String getAddress() {

        return address;
    }

    public void setFirst(String first) {

        this.first = first;
    }

    public void setLast(String last) {

        this.last = last;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    @Override
    public String toString() {
        return this.getLast()+","+ this.getFirst();
    }
}
