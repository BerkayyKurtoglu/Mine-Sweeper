import java.util.ArrayList;
import java.util.HashMap;

public class BusinessLogic {

    private BusinessLogicInterface businessLogicInterface;
    private HashMap<Integer,Boolean> mineMap;
    private int columnNumber;
    private ArrayList<Integer> rightBorderList;
    private ArrayList<Integer> leftBorderList;

    /**
     * Main function to explode buttons according to indexes of mines*/
    public void explodeButtons(
            int i, int rowColumn,
            ArrayList<Integer> borderListForRight,
            ArrayList<Integer> borderListForLeft,
            HashMap<Integer,Boolean> mineMap,
            BusinessLogicInterface businessLogicInterface){
        this.businessLogicInterface = businessLogicInterface;
        this.mineMap = mineMap;
        this.columnNumber = rowColumn;
        this.rightBorderList = borderListForRight;
        this.leftBorderList = borderListForLeft;
        goToUp(i);
        goToDown(i);
        goToRight(i);
        goToLeft(i);
    }

    /**
     * Explodes going right, until face a mine*/
    private void goToRight(int i){
        System.out.println(i);
        while (!leftBorderList.contains(i) && !mineMap.get(i)){
            try {
                businessLogicInterface.disableTheButton(i);
                i++;
            }catch (Exception e){
                break;
            }
        }
        calculatePoint(i,true);
    }

    /**
     * Explodes going left, until face a mine*/
    private void goToLeft(int i){
        while (!rightBorderList.contains(i) && !mineMap.get(i)){
            try {
                //jButtons.get(i).setEnabled(false);
                businessLogicInterface.disableTheButton(i);
                i--;
            }catch (Exception e){
                break;
            }
        }
        calculatePoint(i,false);
    }

    /**
     * Explodes going up, until face a mine*/
    private void goToUp(int i){

        while (!mineMap.get(i)){
            try {
                //jButtons.get(i).setEnabled(false);
                businessLogicInterface.disableTheButton(i);
                goToRight(i);
                goToLeft(i);
                i = i-columnNumber;
            }catch (Exception e){
                break;
            }
        }
    }
    /**
     * Explodes going down, until face a mine*/
    private void goToDown(int i){
        while (!mineMap.get(i)){
            try {
                //jButtons.get(i).setEnabled(false);
                businessLogicInterface.disableTheButton(i);
                goToLeft(i);
                goToRight(i);
                i = i+ columnNumber;
            }catch (Exception e){
                break;
            }
        }
    }

    /**
     * A function to show the number of mines around the button
     * @param i index of current buttons that comes from upper function such as ...
     * @param isFromRight where is this function called */
    private void calculatePoint(int i,boolean isFromRight){
        int buttonPoint = 0;
        if (isFromRight){
            if (leftBorderList.contains(i)){
                int here = i-1;
                if (mineMap.get(here-columnNumber)) buttonPoint++;
                if (mineMap.get(here-columnNumber-1)) buttonPoint++;
                if (mineMap.get(here-1)) buttonPoint++;
                if (mineMap.get(here+columnNumber)) buttonPoint++;
                if (mineMap.get(here+columnNumber-1)) buttonPoint++;
                businessLogicInterface.writeThePoint(here,buttonPoint);
            }else{
                buttonPoint++;
                if (mineMap.get(i-1)) buttonPoint++;
                if (mineMap.get(i-columnNumber)) buttonPoint++;
                if (mineMap.get(i-columnNumber-1)) buttonPoint++;
                if (mineMap.get(i-columnNumber-2)) buttonPoint++;
                if (mineMap.get(i+columnNumber)) buttonPoint++;
                if (mineMap.get(i+columnNumber-1)) buttonPoint++;
                if (mineMap.get(i+columnNumber-2)) buttonPoint++;
                businessLogicInterface.writeThePoint(i-1,buttonPoint);
            }
        }
        if (!isFromRight){
            if (rightBorderList.contains(i)){
                int here = i+1;
                System.out.println("IN RİGHT");
                System.out.println("HERE "+here);
                if (mineMap.get(here-columnNumber)) buttonPoint++;
                if (mineMap.get(here-columnNumber+1)) buttonPoint++;
                if (mineMap.get(here+1)) buttonPoint++;
                if (mineMap.get(here+columnNumber)) buttonPoint++;
                if (mineMap.get(here+columnNumber+1)) buttonPoint++;
                businessLogicInterface.writeThePoint(here,buttonPoint);
            }else{
                System.out.println("NOT IN RİGHT");
                buttonPoint++;
                if (mineMap.get(i-columnNumber)) buttonPoint++;
                if (mineMap.get(i+2)) buttonPoint++;
                if (mineMap.get(i-columnNumber+1)) buttonPoint++;
                if (mineMap.get(i-columnNumber+2)) buttonPoint++;
                if (mineMap.get(i+columnNumber)) buttonPoint++;
                if (mineMap.get(i+columnNumber+1)) buttonPoint++;
                if (mineMap.get(i+columnNumber+2)) buttonPoint++;
                businessLogicInterface.writeThePoint(i+1,buttonPoint);
            }
        }
    }

}
