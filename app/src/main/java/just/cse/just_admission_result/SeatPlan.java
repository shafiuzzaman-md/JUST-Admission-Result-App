package just.cse.just_admission_result;

public class SeatPlan {

    private int id;
    private int roll_number;
    private String unit;
    private String exam_center;
    private String building_name;
    private String floor_venue;
    private String room_no;
    private int roll_from;
    private int roll_to;
    private int total_seat;

    public SeatPlan() {

    }

    public SeatPlan(String exam_center, String building_name) {
        this.exam_center = exam_center;
        this.building_name = building_name;
    }

    public void setExam_center(String exam_center) {
        this.exam_center = exam_center;
    }

    public String getExam_center() {
        return this.exam_center;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getBuilding_name() {
        return this.building_name;
    }
}
