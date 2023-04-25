package web.dao.face;

import java.sql.Connection;

import web.dto.Member;

public interface MemberDao {

	/**
	 * id와 pw가 일치하는 회원이 있는지 DB조회하고 결과 반환
	 * 
	 * 
	 * @param conn
	 * @param loginMember
	 * @return - cnt
	 */
	public int selectCntMemberByUseridUserpw(Connection conn, Member loginMember);

	/**
	 * id를 이용해 DB조회
	 * 
	 * @param conn
	 * @param loginMember
	 * @return
	 */
	public Member selectMemberByUserid(Connection conn, Member loginMember);

	/**
	 * 전달받은 member객체를 DB에 insert하는 메소드
	 * 
	 * @param conn
	 * @param member
	 * @return 처리결과 O/X 
	 */
	public int insert(Connection conn, Member member);


}
