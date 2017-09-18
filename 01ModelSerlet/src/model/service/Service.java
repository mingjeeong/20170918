package model.service;

import model.dao.MemberDao;
import model.dto.MemberDto;
/**
 * ##Model(Service) 서비스(업무) 담당하는 클래스
 * 
 * @author kosta
 *
 */
public class Service {
	// 부서테이블 data access 을 위한 객체 생성
	// DeptDao dao= new DeptDao();
	private static MemberDao dao = MemberDao.getInstance();
	
	public Service() {
	}
	/**
	 * 회원 등록 요청 서비스
	 * 
	 * @param dto
	 *            부서 객체
	 * @return 레코드 추가 성공 행수 , 성공 1, 실패 0
	 */
	public int add(MemberDto dto) {
		// int result = dao.insert(dto);
		// return dao.insert(dto);

		// 등록 요청한 부서번호 중복조회
		// 중복되지 않으면 부서등록
		// 중복되면 오류처리를 컨트롤러에게 전달

		if (!dao.isUserid(dto.getUserid())) {
			return dao.insert(dto);
		}
		return 0;
	}
	/**
	 * id 중복검사
	 * 
	 * @param id
	 * @return
	 */
	public boolean duplicate(String id) {
		if (!dao.isUserid(id)) {// 중복 아이디 존재하지 않으면
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @param deptno
	 * @return
	 */
	public MemberDto get(int deptno) {
		return null;
	}
	
	public String login(String userid, String userpw) {
		return dao.login(userid, userpw);
	}

}
