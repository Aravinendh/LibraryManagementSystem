public class Student {
    public String studentName;
    public String studentID;
    public String bookID;

    public Student(String studentName, String studentID)
    {
        this.studentName = studentName;
        this.studentID = studentID;
        this.bookID = null;
    }
    public Student(String studentName, String studentID, String bookID)
    {
        this.studentName = studentName;
        this.studentID = studentID;
        this.bookID = bookID;
    }
}
