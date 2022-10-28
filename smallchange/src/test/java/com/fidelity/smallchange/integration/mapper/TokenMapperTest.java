package com.fidelity.smallchange.integration.mapper;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.smallchange.model.Token;

@SpringBootTest
@Transactional
class TokenMapperTest {

	@Autowired
	private TokenMapper dao;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testInsertToken() {
		assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "fmtsToken"));
		dao.insertToken(new Token("1234", "", ""));
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "fmtsToken"));
	}
	
	@Test
	void testSelectToken() {
		Token token = dao.getTokenByClientId("1234");
		assertEquals(token,null);
	}
	
	@Test
	void testUpdatesToken() {
		assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "fmtsToken"));
		dao.insertToken(new Token("1234", "", ""));
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "fmtsToken"));
		dao.updateToken(new Token("1234", "2323", ""));
		Token token = dao.getTokenByClientId("1234");
		assertEquals(token.getToken(),"2323");
		assertEquals(token,new Token("1234", "2323", ""));
	}

}
