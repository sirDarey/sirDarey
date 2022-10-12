package preExam;

//Class declared for sub2 table under preview before submission
public class sub2 {
    private int QNo;
    private String status;

    public sub2(int QNo, String status) {
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
