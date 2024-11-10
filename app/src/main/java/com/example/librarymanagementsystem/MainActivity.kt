package com.example.librarymanagementsystem

import Book
import BookAlreadyExists
import BookNotFound
import InvalidQuantity
import Student
import StudentAlreadyExists
import StudentNotFound
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.util.InputMismatchException
import java.util.Scanner


class MainActivity : AppCompatActivity() {

    var books = ArrayList<Book>();
    var students = ArrayList<Student>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var reader: BufferedReader = BufferedReader(FileReader(File("Input.txt")))

        var scan = Scanner(System.`in`);
        while(true)
        {
            lateinit var bookName: String
            lateinit var bookID: String
            lateinit var studentName: String
            lateinit var studentID: String
            var quantity: Int

            println("\nChoices: \n")
            println("1.Add a new book.\n" +
                    "2.Upgrade quantity of a book.\n" +
                    "3.Search a book.\n" +
                    "4.Show all books.\n" +
                    "5.Register Student.\n" +
                    "6.Show all registered students.\n" +
                    "7.Check out book.\n" +
                    "8.Check in book.\n" +
                    "9.Exit application.\n")
            print("Enter your choice: ")
            var opt:Int =Integer.parseInt(reader.readLine())
            println(opt)

            when(opt) {
                1 ->{
                    try {
                        print("Enter book name: ")
                        var temp: String = reader.readLine()
                        bookName = temp
                        println(temp)

                        print("Enter book id: ")
                        temp= reader.readLine()
                        bookID = temp
                        println(temp)

                        print("Enter quantity: ")
                        var t:Int =Integer.parseInt(reader.readLine())
                        quantity = t
                        println(t)

                        addBook(bookName, bookID, quantity)
                    } catch (e: Exception) {
                        println(e.message)
                        scan.nextLine()
                    }
                    break
                }
                2 ->{
                    try {
                        print("Enter book id: ")
                        var temp: String = reader.readLine()
                        bookID = temp
                        println(bookID)

                        print("Enter quantity: ")
                        var t:Int =Integer.parseInt(reader.readLine())
                        quantity = t
                        println(quantity)

                        updateQuantity(bookID, quantity)
                    } catch (e: Exception) {
                        println(e.message)
                        scan.nextLine()
                    }
                    break
                }
                3 ->{
                    try {
                        println("Enter book id: ");
                        var temp: String = reader.readLine()
                        bookID = temp
                        println(temp)

                        if(searchBook(bookID)==null)
                        {
                            throw BookNotFound("Book not found.")
                        }
                    } catch (e : Exception)
                    {
                        println(e.message)
                    }
                    break
                }
                4->{
                    showBooks()
                    break
                }
                5->{
                    println("Enter Student name: ")
                    var temp: String = reader.readLine()
                    studentName = temp
                    println(studentName)

                    println("Enter Student ID: ")
                    temp = reader.readLine()
                    studentID = temp
                    println(studentID)

                    try {
                        registerStudent(studentName, studentID)
                    } catch (e : Exception)
                    {
                        println(e.message)
                    }
                    break
                }
                6->{
                    showStudents()
                    break
                }
                7->{
                    println("Enter book ID: ")
                    var temp: String = reader.readLine()
                    bookID = temp
                    println(temp)
                    println("Enter your ID: ")
                    temp = reader.readLine()
                    studentID = temp
                    println(studentID)

                    try {
                        checkOut(bookID, studentID)
                    } catch (e: Exception)
                    {
                        println(e.message)
                    }
                    break
                }
                8->{
                    println("Enter book ID: ")
                    var temp: String = reader.readLine()
                    bookID = temp
                    println(bookID)

                    println("Enter your ID: ")
                    temp = reader.readLine()
                    studentID = temp
                    println(studentID)

                    try {
                        checkIn(bookID, studentID);
                    } catch (e: Exception)
                    {
                        println(e.message)
                    }
                    break
                }
                9->{
                    println("Exited the application.")
                    return
                }
                else -> println("Enter a valid option.")
            }
        }

    }
    @Throws(BookAlreadyExists::class)
    fun addBook(bookName: String, bookID: String, quantity: Int) {
        val bk: Book? = searchBook(bookID)
        if (bk == null) {
            books.add(Book(bookName, bookID, quantity))
            println("$bookName - Book added to library.")
        } else {
            throw BookAlreadyExists("This book is already in the library.")
        }
    }

    fun showStudents() {
        for (st in students) {
            println(st.studentID + ". " + st.studentName)
        }
    }

    @Throws(InvalidQuantity::class, BookNotFound::class, InputMismatchException::class)
    fun updateQuantity(bookID: String?, quantity: Int){
        var b:Book? = searchBook(bookID)
        if(quantity<0){
            throw InvalidQuantity("Enter a valid quantity of book.")
        }
        if(b==null){
            throw BookNotFound("The book you look for is not available in the library.")
        }
        else{
            b.quantity = quantity
            println("Quantity updated.")
        }
    }

    fun searchBook(bookID: String?) : Book? {
        for (bk:Book in books)
        {
            if(bk.bookID.equals(bookID))
            {
                println("Got the book: ${bk.bookName}")
                return bk
            }
        }
        return null
    }

    fun showBooks(){
        for(bk:Book in books)
        {
            println("${bk.bookID}. ${bk.bookName} - ${bk.quantity}\n")
        }
    }

    fun registerStudent(studentName: String, id:String) : Unit {
        for(st:Student in students){
            if (st.studentID.equals(id)) {
                throw StudentAlreadyExists("Student with entered id already exists.")
            }
        }
        students.add(Student(studentName,id))
        println("$id. $studentName added to list.")
    }

    fun checkOut(bookID: String?, ID: String){
        var bk:Book? = searchBook(bookID)
        var st : Student? = null
        for(s:Student in students){
            if (ID.equals(s.studentID)){
                st=s
            }
        }
        if (st==null){
            throw StudentNotFound("Invalid Student ID.")
        }
        if (bk==null){
            throw BookNotFound("The book you look for is not available in the library. ")
        }
        else{
            if (bk.quantity <= bk.checkOuts){
                println("${bk.bookName} is not currently available.")
            }
            else{
                st.bookID = bk.bookID
                bk.checkOuts++
                println("Check out successful.")
            }
        }
    }

    fun checkIn(bookID: String?, ID: String){
        var bk:Book? = searchBook(bookID)
        var st : Student? = null
        for(s:Student in students){
            if (ID.equals(s.studentID)){
                st=s
            }
        }
        if (st==null){
            throw StudentNotFound("Invalid Student ID.")
        }
        if (bk==null){
            throw BookNotFound("The book you look for is not available in the library. ")
        }
        else{
            if (bk.bookName == null || bk.checkOuts == 0){
                println("Cannot check in.")
            }
            else{
                st.bookID = null
                bk.checkOuts--
                println("Check in successful.")
            }
        }
    }
}