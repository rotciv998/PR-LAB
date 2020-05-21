
package javaapplication5;


class Notification 
{ 
    String notification; 
  
    public Notification(String notification) 
    { 
        this.notification = notification; 
    } 
    public String getNotification() 
    { 
        return notification; 
    } 
} 
  
// Interfață de colectare
interface Collection 
{ 
    public Iterator createIterator(); 
} 
  
//Colectarea notificărilor
class NotificationCollection implements Collection 
{ 
    static final int MAX_ITEMS = 6; 
    int numberOfItems = 0; 
    Notification[] notificationList; 
  
    public NotificationCollection() 
    { 
        notificationList = new Notification[MAX_ITEMS]; 
  
        
        addItem("carttii noi in colectie 1"); 
        addItem("carttii noi in colectie 2"); 
        addItem("carttii noi in colectie 3"); 
        addItem("carttii noi in colectie 4"); 
        addItem("carttii noi in colectie 5"); 
    } 
  
    public void addItem(String str) 
    { 
        Notification notification = new Notification(str); 
        if (numberOfItems >= MAX_ITEMS) 
            System.err.println("Full"); 
        else
        { 
            notificationList[numberOfItems] = notification; 
            numberOfItems = numberOfItems + 1; 
        } 
    } 
  
    public Iterator createIterator() 
    { 
        return new NotificationIterator(notificationList); 
    } 
} 
  

interface Iterator 
{ 

    boolean hasNext(); 
  
    
    Object next(); 
} 
  
// iteratorul de notificari
class NotificationIterator implements Iterator 
{ 
    Notification[] notificationList; 
  
   
    int pos = 0; 
  
    // Constructorul are o serie de notificăriList sunt
    
    public  NotificationIterator (Notification[] notificationList) 
    { 
        this.notificationList = notificationList; 
    } 
  
    public Object next() 
    { 
      // întoarce următorul element din tablou și incrementează poz 
        Notification notification =  notificationList[pos]; 
        pos += 1; 
        return notification; 
    } 
  
    public boolean hasNext() 
    { 
        if (pos >= notificationList.length || 
            notificationList[pos] == null) 
            return false; 
        else
            return true; 
    } 
} 
  

class NotificationBar 
{ 
    NotificationCollection notifications; 
  
    public NotificationBar(NotificationCollection notifications) 
    { 
        this.notifications = notifications; 
    } 
  
    public void printNotifications() 
    { 
        Iterator iterator = notifications.createIterator(); 
        System.out.println("-------Bara notificari------------"); 
        while (iterator.hasNext()) 
        { 
            Notification n = (Notification)iterator.next(); 
            System.out.println(n.getNotification()); 
        } 
    } 
} 
  

class Main 
{ 
    public static void main(String args[]) 
    { 
        NotificationCollection nc = new NotificationCollection(); 
        NotificationBar nb = new NotificationBar(nc); 
        nb.printNotifications(); 
    } 
} 