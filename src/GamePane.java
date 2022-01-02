import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class GamePane extends JFrame implements BusinessLogicInterface {

    private Random random = new Random();
    private final ArrayList<Integer> randomSweetIndex = new ArrayList<>();
    private final ArrayList<JButton> panelArrayList = new ArrayList<>();
    private final HashMap<Integer,Boolean> mineMap = new HashMap<>();
    private final ArrayList<Integer> rightBorderList =  new ArrayList<>();
    private final ArrayList<Integer> leftBorderList =  new ArrayList<>();

    private int rowAndColumnNumber = 10;

    private int clickedNumber = 0;

    private ClickedTheSweetForTwoPlayer clickedTheSweetForTwoPlayer;

    private final GridLayout mainGridLayout = new GridLayout(rowAndColumnNumber,rowAndColumnNumber,5,5);
    private final JPanel mainPanel = new JPanel(mainGridLayout);

    private BusinessLogic businessLogic = new BusinessLogic();


    public GamePane(int rowAndColumnNumber, String userName, ClickedTheSweetForTwoPlayer clickedTheSweetForTwoPlayer) {
        this.rowAndColumnNumber = rowAndColumnNumber;
        mainGridLayout.setColumns(rowAndColumnNumber);
        mainGridLayout.setRows(rowAndColumnNumber);
        drawThePanels(rowAndColumnNumber,userName);
        identifTheBorderIndexes(rowAndColumnNumber);
        this.clickedTheSweetForTwoPlayer = clickedTheSweetForTwoPlayer;
        adjustMainPanel(userName);
    }

    /**
     * Operates the main panel of the game
     * */
    private void adjustMainPanel(String name){
        add(mainPanel);
        setVisible(true);
        setSize(800,800);
        if (name != null)setTitle("Game For "+name);
        else setTitle("One Player Mine Sweeter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Creates random numbers to pick which buttons are going to be a sweet
     * @param number row and column number*/
    private void drawThePanels(int number,String userName){
        if (userName != null) JOptionPane.showMessageDialog(mainPanel,"Game starting for "+userName);
        for (int i = 0; i < (number*number) / 6; i++) {
            int n = random.nextInt(number*number);
            randomSweetIndex.add(n);
        }
        for (int i = 0; i < number*number; i++) {
            JButton jButton  = new JButton();
            jButton.setFocusable(false);
            jButton.setBackground(new Color(197, 197, 197));
            if (randomSweetIndex.contains(i)){
                //jButton.setText("*");
                mineMap.put(i,true);
                jButton.addActionListener(e -> {
                    stopGame(panelArrayList,randomSweetIndex);
                    clickedTheSweetForTwoPlayer.addClickedTheSweetListener(new ResultClass(userName,clickedNumber));
                });
            }else {
                int finalI = i;
                mineMap.put(i,false);
                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        clickedNumber++;
                        if (userName==null) setTitle("Total Clicked : "+clickedNumber);
                        businessLogic.explodeButtons(
                                finalI,rowAndColumnNumber,rightBorderList,leftBorderList,
                                mineMap,GamePane.this);
                        repaint();
                        revalidate();
                    }
                });
            }
            mainPanel.add(jButton);
            panelArrayList.add(jButton);
            revalidate();
            repaint();
        }
    }

    /**
     * When player clicked any mine, stops the game and shows all the places of the mines"*/
    private void stopGame(ArrayList<JButton> buttonArrayList,ArrayList<Integer> sweetList){
        sweetList.forEach(i ->{
            buttonArrayList.get(i).setBackground(Color.RED);
            buttonArrayList.get(i).setText("*");
        });
        buttonArrayList.forEach(b->{
            b.setEnabled(false);
        });
        revalidate();
        repaint();
    }

    /**
     * Identifies the left and right borders of the game pane and adds left border indexes to left border list,
     * adds right border indexes to right border list
     * @param rowAndColumnNumber row and column number entered by user*/
    private void identifTheBorderIndexes(int rowAndColumnNumber){
        int i = 0;
        int leftIndex = 0;
        int rightIndex = rowAndColumnNumber-1;
        while (i!=rowAndColumnNumber){
            leftBorderList.add(leftIndex);
            rightBorderList.add(rightIndex);
            leftIndex = leftIndex+rowAndColumnNumber;
            rightIndex = rightIndex + rowAndColumnNumber ;
            i++;
        }
    }

    @Override
    public void writeThePoint(int i, int number) {
        panelArrayList.get(i).setText(String.valueOf(number));
    }

    @Override
    public void disableTheButton(int i) {
        panelArrayList.get(i).setEnabled(false);
    }
}
