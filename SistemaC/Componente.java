/**
 * @(#)Componente.java
 *
 *
 * @author 
 * @version 1.00 2015/5/15
 */

public class Componente {
	private int id; 
	private String name;
	private String desc;
	private String status;
		
	public Componente(int id, String name, String desc, String status) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDesc() {
		return desc;
	}
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}

	public String toString(){
		return name+" - "+desc+" - "+status;
	}
	
	public Object[] getRow(){
		return new Object[]{id, name, desc, status};
	}
	
	
}
