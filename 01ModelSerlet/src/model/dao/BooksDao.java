package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import model.dto.BooksDto;
import model.dto.MemberDto;

public class BooksDao {
	
	private FactoryDao factory = FactoryDao.getInstance();
	private static BooksDao instance = new BooksDao();
	
	/** db연결을 위한 property 멤버변수 선언 */

	private String driver ;
	private String url ;
	private String username ;
	private String password ;
	private ResourceBundle bundle;

/**
 * 기본 생성자
 */
	private BooksDao() {
		

	}
	
	
	public static BooksDao getInstance() {
		return instance;
	}

	
/**
 * db연결 메소드
 * @return 연결성공시 connection전달
 */
	

/**
 * 도서 추가 메소드
 * @param dto
 * @return
 */


	public int insert(BooksDto dto) {
		
		String bname = dto.getBname();
		String author = dto.getAuthor();
		String publisher = dto.getPublisher();
		String publishdate = dto.getPublishdate();
		String isbn = dto.getIsbn();
		String loc = dto.getLoc();
		
		Connection conn = null;
		PreparedStatement pstm = null;
		
		//insert into books values('언어의 온도','이기주','말글터','2016.08.19','9791195522125','위치');
		String sql = "insert into books values(?,?,?,?,?,?)";

		try {
			conn = factory.getConnection();
			pstm = conn.prepareStatement(sql);//미완성 정적 sql통로
			
			pstm.setString(1, bname);
			pstm.setString(2, author);
			pstm.setString(3, publisher);
			pstm.setString(4, publishdate);
			pstm.setString(5, isbn);
			pstm.setString(6, loc);//완벽한 정적 sql통로
			
			return pstm.executeUpdate(); //sql 수행 :완벽한 통로이므로 sql지정해서는 안됨

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error : 도서 등록오류");

		} 
		factory.close(conn, pstm);
		return 0;
	}

	


	/**
	 * 도서 중복 조회 메서드
	 * @param isbn
	 * @return 존재하면 true 존재하지 않으면 false
	 */
	public boolean isBookIsbn(String isbn) {

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstm = null;
		
		//select * from books where isbn='';
		String sql = "select * from books where isbn=?";

		try {
			conn = factory.getConnection();
			
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, isbn);
			
			rs = pstm.executeQuery();
			return rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error : 도서등록 중복조회 오류");

		} 
		factory.close(conn, pstm);
		
		return false;
	}

	
	/**
	 * 도서 삭제 메소드 
	 * @param bname
	 * @param author
	 * @param publisher
	 * @param isbn
	 * @return삭제성공 true 실패시 false 반환
	 */
	
	public boolean deleteBook(String bname, String author, String publisher, String isbn) {

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstm = null;

		
		String sql = "delete from books where bname=? and author =? and publisher=? and isbn=?";

		try {
			conn = factory.getConnection();
	
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, bname);
			pstm.setString(2, author);
			pstm.setString(3, publisher);
			pstm.setString(4, isbn);

			rs = pstm.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error : 도서삭제 오류");

		} 
		factory.close(conn, pstm);
		return false;
	}
	
	
	/**
	 * 도서정보 변경 메소드  
	 * @param isbn
	 * @param loc
	 * @return 성공시 1, 실패시 0 리턴
	 */
	public int updateBooks(String isbn,String loc) {
		
	

		Connection conn = null;
		PreparedStatement pstm = null;
		
		// update books set loc='' where isbn='';
		String sql = "update books set loc=? where isbn=?";

		try {
			conn = factory.getConnection();
			
			pstm = conn.prepareStatement(sql);// 미완성 정적 sql통로

			pstm.setString(1, loc);// 완벽한 정적 sql통로
			pstm.setString(2, isbn);
			

			return pstm.executeUpdate(); // sql 수행 :완벽한 통로이므로 sql지정해서는 안됨

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error : 도서 정보 변경 오류");

		}
		factory.close(conn, pstm);
		return 0;
	}
	
	/**
	 * isbn 검색
	 * @param isbn
	 * @return
	 */

	public String selectIsbn(String isbn) {

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstm = null;
		
		//select isbn from books where isbn='';
		String sql = "select isbn from books where isbn=?";

		try {
			conn = factory.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, isbn);

			rs = pstm.executeQuery();

			
		
			if (rs.next()) {
				isbn = rs.getString("isbn");
				return isbn;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error : 회원 로그인 오류");

		}
		factory.close(conn, pstm);
		return null;
	}

	
	
/**
 * 도서 상세 조회
 * @param bname
 * @param author
 * @param publisher
 * @return 부서객체, 실패시 null
 */
	public BooksDto select(String bname,String author,String publisher) {

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstm = null;
		String publishdate = null;
		String isbn = null;
		String loc = null;
		//select * from books where bname='user01';
		String sql = "select * from books where bname=? or author =? or publisher=?";

		try {
			conn = factory.getConnection();
			pstm = conn.prepareStatement(sql);
	
			pstm.setString(1, bname);
			pstm.setString(2,author);
			pstm.setString(3,publisher);
			
			rs = pstm.executeQuery();			
			
			if (rs.next()) {
				
				publishdate = rs.getString("publishdate");
				isbn = rs.getString("isbn");
				loc = rs.getString("loc");

				BooksDto dto = new BooksDto(bname, author, publisher, publishdate, isbn, loc);
				return dto;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error : 도서 상세조회 오류");

		}
		factory.close(conn, pstm);
		
		return null;
	}
	
	
	
/**
 * 도서 전체 조회
 * @return
 */
	
	
	public ArrayList<BooksDto> selectAll() {

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstm = null;
		
		//select * from books ;
		String sql = "select * from books";

		try {
			conn = factory.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			ArrayList<BooksDto> list = new ArrayList<BooksDto>();
			
			String bname =null;
			String author = null;
			String publisher = null;
			String publishdate = null;
			String isbn = null;
			String loc = null;
			
			
			while(rs.next()) {
				bname = rs.getString("bname");
				author = rs.getString("author");
				publisher = rs.getString("publisher");
				publishdate = rs.getString("publishdate");
				isbn = rs.getString("isbn");
				loc = rs.getString("loc");
				
				
				list.add(new BooksDto(bname, author, publisher, publishdate, isbn,loc));
				
			}
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error : 도서 전체조회 오류");

		} 
		factory.close(conn, pstm);
		return null;
	}
	
	

	
	/**
	 * 같은 선호도서인 회원 전체 조회
	 * @param preference
	 * @return
	 */

	public HashMap<String, MemberDto> selectPref(String preference) {

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstm = null;

		// select * from members where preference like ('%교육%'); 
		String sql = "select * from members where preference like (?)";

		try {
			conn = factory.getConnection();
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1,"%"+preference+"%");
			rs = pstm.executeQuery();

			HashMap<String, MemberDto> map = new HashMap<String, MemberDto>();
		
			String userid =null;
			String username = null;
			String userpw = null;
			String addr = null;
			String mobile = null;
			String prefer = null;
			

			if (rs.next()) {

				userid = rs.getString("userid");
				username = rs.getString("username");
				userpw = rs.getString("userpw");
				addr = rs.getString("addr");
				mobile = rs.getString("mobile");
				prefer = rs.getString("preference");

				map.put(userid, new MemberDto(userid, username, userpw, addr, mobile, prefer));
				return map;
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error : 회원 선호도서분야 조회 오류");

		}
		factory.close(conn, pstm);
		return null;
	}
}
