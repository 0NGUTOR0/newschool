package com.example.school.dto;

import com.example.school.models.PrimeAccount;
import com.example.school.models.Student;

public class StudentResponseDTO {
    private Long id;
    private String name;
    private String grade;
    private int age;
    private String sex;
    private String address;
    private String religion;
    private String contactEmail;
    private String contactNumber;
    private String nationality;
    private String state;
    private String lga;
    private String imagePath;
    private String addressImagePath;
    private String parentIdPath;
    private PrimeAccount primeAccount;
    private String school;

    // Constructor mapping entity to DTO
    public StudentResponseDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.grade = student.getGrade();
        this.age = student.getAge();
        this.sex = student.getSex();
        this.address = student.getAddress();
        this.religion = student.getReligion();
        this.contactEmail = student.getContactEmail();
        this.contactNumber = student.getContactNumber();
        this.nationality = student.getNationality();
        this.state = student.getState();
        this.lga = student.getLga();
        this.imagePath = student.getImagePath();
        this.addressImagePath = student.getAddressImagePath();
        this.parentIdPath = student.getParentIdPath();
        this.primeAccount = student.getPrimeAccount();
    }

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
    public PrimeAccount getPrimeAccount() { return primeAccount; }
    public void setPrimeAccount(PrimeAccount primeAccount) { this.primeAccount = primeAccount; }
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
}
