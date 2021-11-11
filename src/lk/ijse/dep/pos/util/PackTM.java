package lk.ijse.dep.pos.util;

public class PackTM implements Cloneable{

    private String code;
    private String packName;
    private double unitPrice;
    private String pack01;
    private String pack02;
    private String pack03;
    private String pack04;
    private String pack05;


    public PackTM() {
    }

    public PackTM(String code, String packName, double unitPrice, String pack01, String pack02, String pack03, String pack04, String pack05) {
        this.code = code;
        this.packName = packName;
        this.unitPrice = unitPrice;
        this.pack01 = pack01;
        this.pack02 = pack02;
        this.pack03 = pack03;
        this.pack04 = pack04;
        this.pack05 = pack05;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPack01() {
        return pack01;
    }

    public void setPack01(String pack01) {
        this.pack01 = pack01;
    }

    public String getPack02() {
        return pack02;
    }

    public void setPack02(String pack02) {
        this.pack02 = pack02;
    }

    public String getPack03() {
        return pack03;
    }

    public void setPack03(String pack03) {
        this.pack03 = pack03;
    }

    public String getPack04() {
        return pack04;
    }

    public void setPack04(String pack04) {
        this.pack04 = pack04;
    }

    public String getPack05() {
        return pack05;
    }

    public void setPack05(String pack05) {
        this.pack05 = pack05;
    }

    @Override
    public String toString() {
        return "PackTM{" +
                "code='" + code + '\'' +
                ", packName='" + packName + '\'' +
                ", unitPrice=" + unitPrice +
                ", pack01='" + pack01 + '\'' +
                ", pack02='" + pack02 + '\'' +
                ", pack03='" + pack03 + '\'' +
                ", pack04='" + pack04 + '\'' +
                ", pack05='" + pack05 + '\'' +
                '}';
    }
}
