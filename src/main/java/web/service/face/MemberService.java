package web.service.face;

import javax.servlet.http.HttpServletRequest;

import web.dto.Member;

public interface MemberService {

	/**
	 * req에 담긴 파라미터를 이용하여 유저객체 생성
	 * 
	 * @param req
	 * @return
	 */
	public Member getLoginMember(HttpServletRequest req);

	/**
	 * id와 pw가 일치하는 계정이 있는지 확인
	 * 
	 * @param loginMember - 조회에 쓰이는 member
	 * @return - 있는지 없는지 반환
	 */
	public boolean login(Member loginMember);

	/**
	 * id를 이용해 유저 정보 가져오기
	 * 
	 * @param loginMember
	 * @return - 조회된 유저 정보
	 */
	public Member info(Member loginMember);

	/**
	 * 파라미터를 이용해 유저객체 반환
	 * 
	 * @param req-파라미터가 담긴 요청객체
	 * @return
	 */
	public Member getJoinMember(HttpServletRequest req);

	/**
	 * 전달받은 member객체를 insert하는 메소드
	 * 
	 * @param member
	 */
	public void join(Member member);

}
