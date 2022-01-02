import javax.swing.*;
import java.util.HashMap;

public class ResultPane extends JFrame {

    private JPanel mainPanel;
    private JLabel namePlayerOne;
    private JLabel clickPlayerOne;
    private JLabel namePlayerTwo;
    private JLabel clickPlayerTwo;
    private int i = 1;

    public ResultPane(HashMap<String , Integer> resultMap){
        add(mainPanel);
        setTitle("Result Panel");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,100);
        setLocationRelativeTo(null);
        resultMap.forEach((n,c)->{
            if (i == 1){
                namePlayerTwo.setText(n + " totaly clicked : ");
                clickPlayerTwo.setText(String.valueOf(c));
            }else {
                namePlayerOne.setText(n + " totaly Clicked : ");
                clickPlayerOne.setText(String.valueOf(c));
            }
            i++;
        });
    }
}
