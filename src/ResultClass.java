public class ResultClass {
    private String name;
    private int clickedNumber;

    public ResultClass(String name, int clickedNumber){
        this.name = name;
        this.clickedNumber = clickedNumber;
    }

    public int getClickedNumber() {
        return clickedNumber;
    }

    public String getName() {
        return name;
    }

}
