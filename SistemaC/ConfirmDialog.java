/**
 * @(#)ConfirmSprayer.java
 *
 * @authors Equipo UC
 * @version 1.00 2015/5/14
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

class ConfirmDialog {

    public static final int YES = 0;
    public static final int NO = -1;
    private int choice = NO;

    private JLabel lbTime;
    private JLabel lbQuestion;
    private JButton btnYes;
    private JButton btnCancel;
    private Timer t;

    private void setComponents(){
    	lbTime = new JLabel("Encendiendo rociadores en: 15", JLabel.CENTER);
    	lbQuestion = new JLabel("Encender Ahora?", JLabel.CENTER);
    	btnYes = new JButton("Yes");
    	btnCancel = new JButton("Cancel");
    }

    public int showConfirmDialog(String title) {
    	setComponents();

        btnYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = YES;
                JButton button = (JButton)e.getSource();
                SwingUtilities.getWindowAncestor(button).dispose();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = NO;
                JButton button = (JButton)e.getSource();
                SwingUtilities.getWindowAncestor(button).dispose();
            }
        });

        t = new Timer(1000, new ActionListener() {

            private int counter = 15;

        @Override
        public void actionPerformed(ActionEvent ae) {
           lbTime.setText("Encendiendo rociadores en: "+ --counter);
            	if (counter < 1) {
            		t.stop();
                    btnYes.doClick();
                }
            }
		});
        t.start();

        JPanel buttons = new JPanel();
        buttons.add(btnYes);
        buttons.add(btnCancel);

        JPanel content = new JPanel(new GridLayout(3, 1));
        content.add(lbTime);
        content.add(lbQuestion);
        content.add(buttons);

        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setTitle(title);
        dialog.getContentPane().add(content);
        dialog.setSize(280, 150);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return choice;
    }
}
