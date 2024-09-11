import java.util.*;

public class Main {

    public static ArrayList<Book> books = new ArrayList<>();
    public static ArrayList<Student> Students = new ArrayList<>();

    public static void addBook(String bookName, String bookID, int quantity) throws BookAlreadyExists
    {
        Book bk = searchBook(bookID);
        if(bk==null)
        {
            books.add(new Book(bookName, bookID, quantity));
            System.out.println(bookName + " - Book added to library.");
        }
        else
        {
            throw new BookAlreadyExists("This book is already in the library.");
        }
    }

    public static void updateQuantity(String bookID, int quantity) throws InvalidQuantity, BookNotFound
    {
        Book b = searchBook(bookID);
        if(quantity<0)
        {
            throw new InvalidQuantity("Enter a valid quantity of book.");
        }
        if(b==null)
        {
            throw new BookNotFound("The book you look for is not available in the library.");
        }
        else {
            b.quantity = quantity;
            System.out.println("Quantity updated.");
        }
    }

    public static Book searchBook(String bookID)
    {
        for(Book bk : books)
        {
            if(bk.bookID.equals(bookID))
            {
                System.out.println("Got the book: "+bk.bookName);
                return bk;
            }
        }
        return null;
    }

    public static void showBooks()
    {
        for(Book bk : books)
        {
            System.out.println(bk.bookID + ". " + bk.bookName + " - " + bk.quantity + "\n");
        }
    }

    public static void registerStudent(String studentName, String id) throws StudentAlreadyExists
    {
        for(Student st: Students)
        {
            if(st.studentID.equals(id))
            {
                throw new StudentAlreadyExists("Student with entered id already exists.");
            }
        }
        Students.add(new Student(studentName, id));
        System.out.println(id + ". " + studentName + " added to list.");
    }

    public static void showStudents()
    {
        for(Student st: Students)
        {
            System.out.println(st.studentID + ". " + st.studentName);
        }
    }

    public static void checkOut(String bookID, String ID) throws StudentNotFound, BookNotFound
    {
        Book bk = searchBook(bookID);
        Student st = null;
        for(Student s: Students)
        {
            if(ID.equals((s.studentID)))
            {
                st=s;
            }
        }

        if(st==null)
        {
            throw new StudentNotFound("Invalid Student ID.");
        }
        if(bk==null)
        {
            throw new BookNotFound("The book you look for is not available in the library.");
        }

        else
        {
            if(bk.quantity<= bk.checkOuts)
            {
                System.out.println(bk.bookName + " is not currently available.");
            }
            else
            {
                st.bookID = bk.bookID;
                bk.checkOuts++;
                System.out.println("Check out successful.");
            }
        }
    }

    public static void checkIn(String bookID, String ID) throws StudentNotFound, BookNotFound
    {
        Book bk = searchBook(bookID);
        Student st = null;
        for(Student s: Students)
        {
            if(ID.equals((s.studentID)))
            {
                st=s;
            }
        }

        if(st==null)
        {
            throw new StudentNotFound("Invalid Student ID.");
        }
        if(bk==null)
        {
            throw new BookNotFound("The book you look for is not available in the library.");
        }

        else
        {
            if(bk.bookName == null || bk.checkOuts==0 )
            {
                System.out.println("Cannot check in.");
            }
            else
            {
                st.bookID = null;
                bk.checkOuts--;
                System.out.println("Check in successful.");
            }
        }
    }


    public static void main(String[] args) {


        Scanner scan = new Scanner(System.in);
        while(true)
        {

            String bookName;
            String bookID;
            String studentName;
            String studentID;
            int quantity;

            System.out.println("\nChoices: \n");
            System.out.println("1.Add a new book.\n2.Upgrade quantity of a book.\n3.Search a book.\n4.Show all books.\n5.Register Student.\n6.Show all registered students.\n7.Check out book.\n8.Check in book.\n9.Exit application.\n");
            System.out.print("Enter your choice: ");
            int opt = scan.nextInt();

            switch(opt) {
                case 1:
                    System.out.print("Enter book name: ");
                    scan.nextLine();
                    bookName = scan.nextLine();

                    System.out.print("Enter book id: ");
                    bookID = scan.nextLine();

                    System.out.print("Enter quantity: ");
                    quantity = scan.nextInt();

                    addBook(bookName, bookID, quantity);
                    break;

                case 2:
                    System.out.print("Enter book id: ");
                    scan.nextLine();
                    bookID = scan.nextLine();

                    System.out.print("Enter quantity: ");
                    quantity = scan.nextInt();

                    updateQuantity(bookID, quantity);
                    break;

                case 3:
                    System.out.print("Enter book id: ");
                    scan.nextLine();
                    bookID = scan.nextLine();

                    if(searchBook(bookID) == null)
                    {
                        System.out.println("Book not found.");
                    }
                    break;

                case 4:
                    showBooks();
                    break;

                case 5:
                    System.out.print("Enter Student name: ");
                    scan.nextLine();
                    studentName = scan.nextLine();

                    System.out.print("Enter Student ID: ");
                    studentID = scan.nextLine();

                    registerStudent(studentName, studentID);
                    break;

                case 6:
                    showStudents();
                    break;

                case 7:
                    scan.nextLine();
                    System.out.print("Enter book ID: ");
                    bookID = scan.nextLine();
                    System.out.print("Enter your ID: ");
                    studentID = scan.nextLine();

                    checkOut(bookID, studentID);
                    break;

                case 8:
                    scan.nextLine();
                    System.out.print("Enter book ID: ");
                    bookID = scan.nextLine();
                    System.out.print("Enter your ID: ");
                    studentID = scan.nextLine();

                    checkIn(bookID, studentID);
                    break;

                case 9:
                    System.out.println("Exited the application.");
                    return;

                default:
                    System.out.println("Enter a valid option.");
            }
        }
    }
}