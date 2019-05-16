package com.company.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class EmployeeTest {

	@Test
	public void employee() {

		Employee emp = new Employee();
		emp.setId(1);
		emp.setUserid("test");
		emp.setPass("testpass");
		emp.setName("テスト");
		emp.setAge(20);
		emp.setSex("男性");
		emp.setAddress("address");
		emp.setTel("tel");

		assertThat(emp.getId()).isEqualTo(1);
		assertThat(emp.getUserid()).isEqualTo("test");
		assertThat(emp.getPass()).isEqualTo("testpass");
		assertThat(emp.getName()).isEqualTo("テスト");
		assertThat(emp.getAge()).isEqualTo(20);
		assertThat(emp.getSex()).isEqualTo("男性");
		assertThat(emp.getAddress()).isEqualTo("address");
		assertThat(emp.getTel()).isEqualTo("tel");
		assertThat(emp.toString()).isEqualTo("Employee [id=1, userid=test, pass=testpass, name=テスト, age=20, sex=男性, address=address, tel=tel]");
	}

}
