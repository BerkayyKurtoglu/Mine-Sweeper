import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class EntryPane extends JFrame implements ClickedTheSweetForTwoPlayer {

    private JPanel mainPanel;
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JButton startTheGameButton;
    private JRadioButton twoPlayerButton;
    private JRadioButton onePlayerButton;
    private JTextField playerOneName;
    private JTextField playerTwoName;
    private JLabel label1;
    private JLabel label2;

    private String userOneName = "";
    private String userTwoName = "";
    private int columnRowNumber = 10;

    public EntryPane(){
        manageTextFields();
        manageCheckBoxes();
        manageButton();
        playerOneName.setVisible(false);
        playerTwoName.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(false);
        add(mainPanel);
        setTitle("Welcome to The Mine Sweeter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,200);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void manageCheckBoxes(){
        buttonGroup.add(twoPlayerButton);
        buttonGroup.add(onePlayerButton);

        twoPlayerButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (twoPlayerButton.isSelected()){
                    playerOneName.setVisible(true);
                    playerTwoName.setVisible(true);
                    label1.setVisible(true);
                    label2.setVisible(true);
                    repaintPane();
                }
            }
        });
        onePlayerButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (onePlayerButton.isSelected()){
                    playerOneName.setVisible(false);
                    playerTwoName.setVisible(false);
                    label1.setVisible(false);
                    label2.setVisible(false);
                }
            }
        });

    }

    private void manageTextFields() {
        playerOneName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                userOneName = playerOneName.getText();
            }
        });
        playerTwoName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                userTwoName = playerTwoName.getText();
            }
        });
    }

    private void manageButton(){
        startTheGameButton.addActionListener(e -> {
            askColumnRowNumber();
            if (twoPlayerButton.isSelected()){
                //Two Player
                if (userOneName.isBlank() || userTwoName.isBlank()){
                    JOptionPane.showMessageDialog(mainPanel,"Please enter the names");
                }else{
                    setVisible(false);
                    new GamePane(columnRowNumber, userOneName,EntryPane.this);
                }
            }else{
                //One Player
                setVisible(false);
                new GamePane(columnRowNumber, null, playerInfo -> {});
            }
        });

    }

    private void askColumnRowNumber(){
        try {
            columnRowNumber = Integer.parseInt(
                    JOptionPane.showInputDialog(mainPanel,"Enter row and column number",columnRowNumber)
            );
        }catch (Exception a){
            JOptionPane.showMessageDialog(mainPanel,
                    "Please Enter an integer number");
            askColumnRowNumber();
        }
    }

    private void repaintPane(){
        revalidate();
        repaint();
    }

    @Override
    public void addClickedTheSweetListener(ResultClass playerInfo) {
        HashMap<String, Integer> list = new HashMap<>();
        list.put(playerInfo.getName(),playerInfo.getClickedNumber());
        new GamePane(columnRowNumber, userTwoName, playerInfo1 -> {
            list.put(playerInfo1.getName(),playerInfo1.getClickedNumber());
            new ResultPane(list);
        });
    }
}