package entitys;


import com.google.gson.Gson;

public class WeiBoEntity {


	public String isVip;
	public String level;
	public String name;
	public String avatar;
	public String url;
	//1，男; 2，女
	public String gender;
	//简介
	public String intro;
	public String tag;
	public String fans;
	public String wbnum;
	public String area;
	//认证类型(没有认证则为空): 1黄; 2蓝; 3达人
	public String att_type;
	//认证信息
	public String att_info;
	
	public String getIsVip() {
		return isVip;
	}
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avtar) {
		this.avatar = avtar;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getFans() {
		return fans;
	}
	public void setFans(String fans) {
		this.fans = fans;
	}
	public String getWbnum() {
		return wbnum;
	}
	public void setWbnum(String wbnum) {
		this.wbnum = wbnum;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAtt_type() {
		return att_type;
	}
	public void setAtt_type(String att_type) {
		this.att_type = att_type;
	}
	public String getAtt_info() {
		return att_info;
	}
	public void setAtt_info(String att_info) {
		this.att_info = att_info;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
