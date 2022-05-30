public class User {
    protected int cid;
    protected String username;
    protected String password;
    protected String role;
    protected boolean coupon = true;

    public boolean getCoupon() {
        return this.coupon;
    }
    public void setCoupon(boolean coupon) {
        this.coupon = coupon;
    }


    public int getCid() {
        return this.cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return this.role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CID: " + getCid() + " Username: " + getUsername() + " Password: " + getPassword() + " Role: " + getRole();
    }
}