package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Double age;

    @Column(name = "basic_sallary")
    private Double basicSallary;

    @Column(name = "employment_date")
    private LocalDate employmentDate;

    @Column(name = "number_of_dependants")
    private Integer numberOfDependants;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Employee name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAge() {
        return this.age;
    }

    public Employee age(Double age) {
        this.age = age;
        return this;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public Double getBasicSallary() {
        return this.basicSallary;
    }

    public Employee basicSallary(Double basicSallary) {
        this.basicSallary = basicSallary;
        return this;
    }

    public void setBasicSallary(Double basicSallary) {
        this.basicSallary = basicSallary;
    }

    public LocalDate getEmploymentDate() {
        return this.employmentDate;
    }

    public Employee employmentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
        return this;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Integer getNumberOfDependants() {
        return this.numberOfDependants;
    }

    public Employee numberOfDependants(Integer numberOfDependants) {
        this.numberOfDependants = numberOfDependants;
        return this;
    }

    public void setNumberOfDependants(Integer numberOfDependants) {
        this.numberOfDependants = numberOfDependants;
    }

    public User getUser() {
        return this.user;
    }

    public Employee user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", age=" + getAge() +
            ", basicSallary=" + getBasicSallary() +
            ", employmentDate='" + getEmploymentDate() + "'" +
            ", numberOfDependants=" + getNumberOfDependants() +
            "}";
    }
}
