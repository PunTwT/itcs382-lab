package parcelpoint;

public class Parcel {
    private String code;
    private String studentId;
    private int size;          // 1 = small, 2 = medium, 3 = large
    public String status;

    public Parcel(String code, String studentId, int size) {
        this.code = code;
        this.studentId = studentId;
        this.size = size;
        this.status = "NEW";
    }

    public String getStatus() {
        return status;
    }

    public void markStored() {
        status = "STORED";
    }

    public void markPickedUp() {
        status = "PICKED_UP";
    }

    public String getCode() {
        return code;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getSize() {
        return size;
    }
}
