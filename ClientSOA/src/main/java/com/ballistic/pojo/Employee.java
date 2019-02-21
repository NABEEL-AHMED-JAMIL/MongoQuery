package com.ballistic.pojo;

import com.google.gson.Gson;
import org.mongodb.morphia.annotations.*;

import java.util.Date;
import java.util.List;

@Entity(value = "employees", noClassnameStored=true)
@Indexes(@Index(value = "firstName", fields = @Field("firstName")))
public class Employee {

    @Id
    private String employeeId;
    @Property("lastName")
    private String lastName;
    @Property("firstName")
    private String firstName;
    @Property("birthDate")
    private Date birthDate;
    @Property("photo")
    private String photo;
    @Property("notes")
    private List<String> notes;

    public Employee() { }

    public Employee(String employeeId, String photo) {
        this.employeeId = employeeId;
        this.photo = photo;
    }

    public Employee(String lastName, String firstName, Date birthDate, String photo, List<String> notes) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.photo = photo;
        this.notes = notes;
    }

    public Employee(String employeeId, String lastName, String firstName, Date birthDate, String photo, List<String> notes) {
        this.employeeId = employeeId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.photo = photo;
        this.notes = notes;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public List<String> getNotes() { return notes; }
    public void setNotes(List<String> notes) { this.notes = notes; }

    @Override
    public String toString() { return new Gson().toJson(this); }
}
