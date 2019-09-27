package kr.co.itcen.mysite.vo;

public class BoardVo {
	private Long no;
	private String title;
	private String context;
	private Long hit;
	private String regDate;
	private Long g_no;
	private Long o_no;
	private Long depth;
	private Long userNo;
	private String name;
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Long getHit() {
		return hit;
	}
	public void setHit(Long hit) {
		this.hit = hit;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public Long getG_no() {
		return g_no;
	}
	public void setG_no(Long g_no) {
		this.g_no = g_no;
	}
	public Long getO_no() {
		return o_no;
	}
	public void setO_no(Long o_no) {
		this.o_no = o_no;
	}
	public Long getDepth() {
		return depth;
	}
	public void setDepth(Long depth) {
		this.depth = depth;
	}
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", context=" + context + ", hit=" + hit + ", regDate="
				+ regDate + ", g_no=" + g_no + ", o_no=" + o_no + ", depth=" + depth + ", userNo=" + userNo + ", name="
				+ name + ", status=" + status + "]";
	}
	

	
	
	
}
