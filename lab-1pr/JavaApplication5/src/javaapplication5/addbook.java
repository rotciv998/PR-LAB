
package javaapplication5;
import java.util.HashMap; 
import java.util.Map; 


abstract class Carti implements Cloneable 
{ 
	
	protected String cartenume; 
	
	abstract void addCarte(); 
	
	public Object clone() 
	{ 
		Object clone = null; 
		try
		{ 
			clone = super.clone(); 
		} 
		catch (CloneNotSupportedException e) 
		{ 
			e.printStackTrace(); 
		} 
		return clone; 
	} 
} 

class CarteColor extends Carti 
{ 
	public CarteColor() 
	{ 
		this.cartenume = "carte1"; 
	} 

	@Override
	void addCarte() 
	{ 
		System.out.println("carte1 adaugata"); 
	} 
	
} 

class carteCopii extends Carti{ 

	public carteCopii() 
	{ 
		this.cartenume= "carte23"; 
	} 

	@Override
	void addCarte() 
	{ 
		System.out.println("carte23 adaugat"); 
	} 
} 

class ColorStore { 

	private static Map<String, Carti> cartiMap = new HashMap<String, Carti>(); 
	
	static
	{ 
		cartiMap.put("color", new CarteColor()); 
		cartiMap.put("copii", new carteCopii()); 
	} 
	
	public static Carti getBook(String BookName) 
	{ 
		return (Carti) cartiMap.get(BookName).clone(); 
	} 
} 



class Prototype 
{ 
	public static void main (String[] args) 
	{ 
		ColorStore.getBook("color").addCarte(); 
		ColorStore.getBook("copii").addCarte(); 
		ColorStore.getBook("copii").addCarte(); 
		ColorStore.getBook("color").addCarte(); 
	} 
} 
