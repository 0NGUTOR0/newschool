package com.example.school.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "students")
public class Student {

   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonProperty("LGA")
    private String lga;

    private String address;

    private int age;

    private String grade;

    private String name;

    private String nationality;

    private String religion;

    private String sex;

    private String state;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "addressimage")
    private String addressImagePath;

    @Column(name = "image")
    private String imagePath;

    @Column(name = "parentid")
    private String parentIdPath;

    @ManyToOne
    private PrimeAccount primeAccount;

    @Column(name = "school")
    private String school;


    // This is the constructor that matches your code
    public Student(String name, String grade, int age, String sex, String address, String religion,
    String contactEmail, String contactNumber, String nationality, String state,
    String lga, String imagePath, String addressImagePath, String parentIdPath,
    PrimeAccount primeAccount, String school) {
this.name = name;
this.grade = grade;
this.age = age;
this.sex = sex;
this.address = address;
this.religion = religion;
this.contactEmail = contactEmail;
this.contactNumber = contactNumber;
this.nationality = nationality;
this.state = state;
this.lga = lga;
this.imagePath = imagePath;
this.addressImagePath = addressImagePath;
this.parentIdPath = parentIdPath;
this.primeAccount = primeAccount;
this.school = school;
}


    // No-args constructor (required by JPA)
    public Student() {}



    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getReligion() { return religion; }
    public void setReligion(String religion) { this.religion = religion; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getLga() { return lga; }
    public void setLga(String lga) { this.lga = lga; }    
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public String getAddressImagePath() { return addressImagePath; }
    public void setAddressImagePath(String addressImagePath) { this.addressImagePath = addressImagePath; }
    public String getParentIdPath() { return parentIdPath; }
    public void setParentIdPath(String parentIdPath) { this.parentIdPath = parentIdPath; }
    public PrimeAccount getPrimeAccount() { return primeAccount;}
    public void setPrimeAccount(PrimeAccount primeAccount) { this.primeAccount = primeAccount; }
   
}