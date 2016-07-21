package entitys;

public class WbyWBEntity extends BasicEntity {
	
	public String gender;
	public String tweet_price;
	public int area_id;
	public String area_name;
	public String weibo_name;
	public String soft_retweet_price;
	public String face_url;
	public String account_id;
	public String url;
	public String followers_count;
	public String retweet_price;
	public int is_vip;
	public int is_bluevip;
	public int is_daren;
	public int is_famous;
	public String verification_info;
	

	
	public int getIs_famous() {
		return is_famous;
	}
	public String getVerification_info() {
		return verification_info;
	}
	public int getIs_vip() {
		return is_vip;
	}
	public int getIs_bluevip() {
		return is_bluevip;
	}
	public int getIs_daren() {
		return is_daren;
	}
	public String getRetweet_price() {
		return retweet_price;
	}
	public void setRetweet_price(String retweet_price) {
		this.retweet_price = retweet_price;
	}
	public String getFollowers_count() {
		return followers_count;
	}
	public void setFollowers_count(String followers_count) {
		this.followers_count = followers_count;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTweet_price() {
		return tweet_price;
	}
	public void setTweet_price(String tweet_price) {
		this.tweet_price = tweet_price;
	}
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getWeibo_name() {
		return weibo_name;
	}
	public void setWeibo_name(String weibo_name) {
		this.weibo_name = weibo_name;
	}
	public String getSoft_retweet_price() {
		return soft_retweet_price;
	}
	public void setSoft_retweet_price(String soft_retweet_price) {
		this.soft_retweet_price = soft_retweet_price;
	}
	public String getFace_url() {
		return face_url;
	}
	public void setFace_url(String face_url) {
		this.face_url = face_url;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	
}
