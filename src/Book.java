import java.util.ArrayList;

public class Book {
    public String bookName;
    public String bookID;
    public int quantity;
    public int checkOuts;
    //public ArrayList<String>students;

    public static int i=0;

    public Book(String bookName, String bookID, int quantity)
    {
        this.bookName = bookName;
        this.bookID = bookID;
        this.quantity = quantity;
    }
}
