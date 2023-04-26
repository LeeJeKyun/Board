package web.dto;

import java.util.Date;

public class Comment {
	
	private int commentno;
	private int boardno;
	private String userid;
	private String content;
	private Date writeDate;
	
	public Comment() {}

	public Comment(int commentno, int boardno, String userid, String content, Date write_date) {
		super();
		this.commentno = commentno;
		this.boardno = boardno;
		this.userid = userid;
		this.content = content;
		this.writeDate = write_date;
	}

	@Override
	public String toString() {
		return "Comment [commentno=" + commentno + ", boardno=" + boardno + ", userid=" + userid + ", content="
				+ content + ", write_date=" + writeDate + "]";
	}

	public int getCommentno() {
		return commentno;
	}

	public void setCommentno(int commentno) {
		this.commentno = commentno;
	}

	public int getBoardno() {
		return boardno;
	}

	public void setBoardno(int boardno) {
		this.boardno = boardno;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	
	
	
}
