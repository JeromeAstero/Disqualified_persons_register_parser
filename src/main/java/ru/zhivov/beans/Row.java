package ru.zhivov.beans;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.util.Date;
import java.util.Objects;


public class Row {
    //Номер записи из реестра дисквалифицированных лиц
    @CsvBindByPosition(position = 0)
    private Long rowNum;

    //ФИО
    @CsvBindByPosition(position = 1)
    private String fullName;

    //Дата рождения ФЛ
    @CsvBindByPosition(position = 2)
    @CsvDate(value = "dd.MM.yyyy")
    private Date birthDate;

    //Место рождения ФЛ
    @CsvBindByPosition(position = 3)
    private String birthPlace;

    //Наименование организации, где ФЛ работало во время совершения правонарушения
    @CsvBindByPosition(position = 4)
    private String organizationName;

    //ИНН организации
    @CsvBindByPosition(position = 5)
    private Long INN;

    //Должность, в которой ФЛ работало во время совершения правонарушения
    @CsvBindByPosition(position = 6)
    private String postOfPerson;

    //Cтатья КоАП РФ
    @CsvBindByPosition(position = 7)
    private String administrativeCode;

    //Наименование органа, составившего протокол об административном правонарушении
    @CsvBindByPosition(position = 8)
    private String organizationMadeProtocol;

    //ФИО судьи, вынесшего постановление о дисквалификации
    @CsvBindByPosition(position = 9)
    private String fullNameJudge;

    //Должность судьи
    @CsvBindByPosition(position = 10)
    private String postOfJudge;

    //Cрок дисквалификации
    @CsvBindByPosition(position = 11)
    private String periodOfIneligibility;

    //Дата начала
    @CsvBindByPosition(position = 12)
    @CsvDate(value = "dd.MM.yyyy")
    private Date startDateOfIneligibility;

    //Дата окончания
    @CsvBindByPosition(position = 13)
    @CsvDate(value = "dd.MM.yyyy")
    private Date endDateOfIneligibility;

    public Row() {
    }

    public Row(Long rowNum, String fullName, Date birthDate, String birthPlace, String organizationName, Long INN, String postOfPerson, String administrativeCode, String organizationMadeProtocol, String fullNameJudge, String postOfJudge, String periodOfIneligibility, Date startDateOfIneligibility, Date endDateOfIneligibility) {
        this.rowNum = rowNum;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.organizationName = organizationName;
        this.INN = INN;
        this.postOfPerson = postOfPerson;
        this.administrativeCode = administrativeCode;
        this.organizationMadeProtocol = organizationMadeProtocol;
        this.fullNameJudge = fullNameJudge;
        this.postOfJudge = postOfJudge;
        this.periodOfIneligibility = periodOfIneligibility;
        this.startDateOfIneligibility = startDateOfIneligibility;
        this.endDateOfIneligibility = endDateOfIneligibility;
    }

    public Long getRowNum() {
        return rowNum;
    }

    public void setRowNum(Long rowNum) {
        this.rowNum = rowNum;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Long getINN() {
        return INN;
    }

    public void setINN(Long INN) {
        this.INN = INN;
    }

    public String getPostOfPerson() {
        return postOfPerson;
    }

    public void setPostOfPerson(String postOfPerson) {
        this.postOfPerson = postOfPerson;
    }

    public String getAdministrativeCode() {
        return administrativeCode;
    }

    public void setAdministrativeCode(String administrativeCode) {
        this.administrativeCode = administrativeCode;
    }

    public String getOrganizationMadeProtocol() {
        return organizationMadeProtocol;
    }

    public void setOrganizationMadeProtocol(String organizationMadeProtocol) {
        this.organizationMadeProtocol = organizationMadeProtocol;
    }

    public String getFullNameJudge() {
        return fullNameJudge;
    }

    public void setFullNameJudge(String fullNameJudge) {
        this.fullNameJudge = fullNameJudge;
    }

    public String getPostOfJudge() {
        return postOfJudge;
    }

    public void setPostOfJudge(String postOfJudge) {
        this.postOfJudge = postOfJudge;
    }

    public String getPeriodOfIneligibility() {
        return periodOfIneligibility;
    }

    public void setPeriodOfIneligibility(String periodOfIneligibility) {
        this.periodOfIneligibility = periodOfIneligibility;
    }

    public Date getStartDateOfIneligibility() {
        return startDateOfIneligibility;
    }

    public void setStartDateOfIneligibility(Date startDateOfIneligibility) {
        this.startDateOfIneligibility = startDateOfIneligibility;
    }

    public Date getEndDateOfIneligibility() {
        return endDateOfIneligibility;
    }

    public void setEndDateOfIneligibility(Date endDateOfIneligibility) {
        this.endDateOfIneligibility = endDateOfIneligibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return rowNum.equals(row.rowNum) &&
                fullName.equals(row.fullName) &&
                birthDate.equals(row.birthDate) &&
                birthPlace.equals(row.birthPlace) &&
                organizationName.equals(row.organizationName) &&
                INN.equals(row.INN) &&
                Objects.equals(postOfPerson, row.postOfPerson) &&
                Objects.equals(administrativeCode, row.administrativeCode) &&
                organizationMadeProtocol.equals(row.organizationMadeProtocol) &&
                fullNameJudge.equals(row.fullNameJudge) &&
                Objects.equals(postOfJudge, row.postOfJudge) &&
                periodOfIneligibility.equals(row.periodOfIneligibility) &&
                Objects.equals(startDateOfIneligibility, row.startDateOfIneligibility) &&
                endDateOfIneligibility.equals(row.endDateOfIneligibility);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNum, fullName, birthDate, birthPlace, organizationName, INN, postOfPerson, administrativeCode, organizationMadeProtocol, fullNameJudge, postOfJudge, periodOfIneligibility, startDateOfIneligibility, endDateOfIneligibility);
    }

    @Override
    public String toString() {
        return "Row{" +
                "rowNum=" + rowNum +
                ", fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                ", birthPlace='" + birthPlace + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", INN=" + INN +
                ", postOfPerson='" + postOfPerson + '\'' +
                ", administrativeCode='" + administrativeCode + '\'' +
                ", organizationMadeProtocol='" + organizationMadeProtocol + '\'' +
                ", fullNameJudge='" + fullNameJudge + '\'' +
                ", postOfJudge='" + postOfJudge + '\'' +
                ", periodOfIneligibility='" + periodOfIneligibility + '\'' +
                ", startDateOfIneligibility=" + startDateOfIneligibility +
                ", endDateOfIneligibility=" + endDateOfIneligibility +
                '}';
    }
}
