package preExam;

//Class declared for english table under preview before submission
public class english {
    private int QNo;
    private String status;

    public english(int QNo, String status) {
        this.QNo = QNo;
        this.status = status;
    }

    public int getQNo() {
        return QNo;
    }

    public void setQNo(int QNo) {
        this.QNo = QNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
