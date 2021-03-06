package com.company.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@SequenceGenerator(name = "id_seq", sequenceName = "id_seq", allocationSize = 1, initialValue = 1)
public class Employee {

	@Id
	@GeneratedValue(generator = "id_seq")
	private long id;

	@NotEmpty
	@Column(name = "user_id")
	private String userid;

	@NotEmpty
	@Column(name = "pass")
	private String pass;

	@NotEmpty
	@Size(max=10)
	@Column(name = "name")
	private String name;

	@Min(value = 0)
	@Max(value = 150)
	@Column(name = "age")
	private int age;

	@NotEmpty
	@Column(name = "sex")
	private String sex;

	@Column(name = "address")
	private String address;

	@Column(name = "tel")
	private String tel;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", userid=" + userid + ", pass=" + pass + ", name=" + name + ", age=" + age + ", sex=" + sex + ", address=" + address + ", tel=" + tel + "]";
	}
}
