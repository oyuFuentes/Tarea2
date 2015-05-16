/**
 * @(#)ServiceMonitorTable.java
 *
 *
 * @author 
 * @version 1.00 2015/5/15
 */

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class ServiceMonitorTable extends JFrame {
	DefaultTableModel modelo;	
	JTable tabla;
	
	public ServiceMonitorTable(String title){
		super(title);
		modelo = new DefaultTableModel();
		tabla = new JTable(modelo);
		setTable();
		JScrollPane scroll = new JScrollPane(tabla);
		
		this.getContentPane().setLayout(new BorderLayout(10, 10));
		this.getContentPane().add("North", new JLabel("Service of components monitoring", JLabel.CENTER));
		this.getContentPane().add(scroll);
		
		Border border =  BorderFactory.createEmptyBorder(10, 20, 20, 20);
		((JPanel)this.getContentPane()).setBorder(border);
		
		setSize(500, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	private void setTable(){
		tabla.setDefaultRenderer (Object.class, new MiRender());
		modelo.addColumn("ID");
		modelo.addColumn("Nombre");
		modelo.addColumn("Descripcion");
		modelo.addColumn("Status");		 
		tabla.getColumnModel().getColumn(0).setMaxWidth(25);
		tabla.getColumnModel().getColumn(1).setMaxWidth(100);
		tabla.getColumnModel().getColumn(2).setMaxWidth(300);
		tabla.getColumnModel().getColumn(3).setMaxWidth(50);
											
	} 
	public void updateStatus(int id, String status){
		for(int row = 0; row < modelo.getRowCount(); row++) {
			if(modelo.getValueAt(row, 0).equals(id)){
				modelo.setValueAt(status, row, 3); 
			}
		}
	}
	public void addComponent(int id, Componente comp){
		modelo.addRow(comp.getRow());					
	}	

}

class MiRender extends DefaultTableCellRenderer
{
   public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column){
      super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
      if ( value.equals("OFF") )
      {
         this.setOpaque(true);
         this.setBackground(Color.RED);
         this.setForeground(Color.WHITE);
      } else {
	     this.setOpaque(true);
         this.setBackground(Color.WHITE);
         this.setForeground(Color.BLACK);         
      }
      return this;
   }
}
