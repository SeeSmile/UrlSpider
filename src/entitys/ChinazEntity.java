package entitys;

import com.google.gson.Gson;

public class ChinazEntity {
	
	private String title;
	private String image;
	private String href;
	private String count_alexa;
	private String count_baidu;
	private String count_pr;
	private String count_link;
	private String introduce;
	private String ranking_all;
	private String ranking_area;
	private String ranking_net;
	private String star;
	private String type;
	private String area;
	private String url;
	
	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getArea() {
		return area;
	}



	public void setArea(String area) {
		this.area = area;
	}



	public String getStar() {
		return star;
	}



	public void setStar(String star) {
		this.star = star;
	}



	public String getIntroduce() {
		return introduce;
	}



	public String getHref() {
		return href;
	}



	public void setHref(String href) {
		this.href = href;
	}



	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}



	public String getTitle() {
		return title;
	}



	public String getImage() {
		return image;
	}



	public String getCount_alexa() {
		return count_alexa;
	}



	public String getCount_baidu() {
		return count_baidu;
	}



	public String getCount_pr() {
		return count_pr;
	}



	public String getCount_link() {
		return count_link;
	}



	public String getRanking_all() {
		return ranking_all;
	}



	public String getRanking_area() {
		return ranking_area;
	}



	public String getRanking_net() {
		return ranking_net;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public void setCount_alexa(String count_alexa) {
		this.count_alexa = count_alexa;
	}



	public void setCount_baidu(String count_baidu) {
		this.count_baidu = count_baidu;
	}



	public void setCount_pr(String count_pr) {
		this.count_pr = count_pr;
	}



	public void setCount_link(String count_link) {
		this.count_link = count_link;
	}



	public void setRanking_all(String ranking_all) {
		this.ranking_all = ranking_all;
	}



	public void setRanking_area(String ranking_area) {
		this.ranking_area = ranking_area;
	}



	public void setRanking_net(String ranking_net) {
		this.ranking_net = ranking_net;
	}



	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
