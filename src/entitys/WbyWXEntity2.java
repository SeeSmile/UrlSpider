package entitys;

import com.google.gson.Gson;

public class WbyWXEntity2 {

	public String face_url;
	public String url;
	public int area_id;
	public String multi_graphic_tup_read_num;
	public String weibo_id;
	public String weibo_name;
	public String area_name;
	public String followers_count;
	public String screen_shot_qr_code;
	public Price gross_deal_price;
	
	public String getWeibo_id() {
		return weibo_id;
	}
	public void setWeibo_id(String weibo_id) {
		this.weibo_id = weibo_id;
	}
	public Price getGross_deal_price() {
		return gross_deal_price;
	}
	public void setGross_deal_price(Price gross_deal_price) {
		this.gross_deal_price = gross_deal_price;
	}

	public class Price{
		
		public String multi_graphic_second_price;
		public String multi_graphic_top_price;
		public String single_graphic_price;
		
		public String getSingle_graphic_price() {
			return single_graphic_price;
		}
		public void setSingle_graphic_price(String single_graphic_price) {
			this.single_graphic_price = single_graphic_price;
		}
		public String getMulti_graphic_second_price() {
			return multi_graphic_second_price;
		}
		public void setMulti_graphic_second_price(String multi_graphic_second_price) {
			this.multi_graphic_second_price = multi_graphic_second_price;
		}
		public String getMulti_graphic_top_price() {
			return multi_graphic_top_price;
		}
		public void setMulti_graphic_top_price(String multi_graphic_top_price) {
			this.multi_graphic_top_price = multi_graphic_top_price;
		}
		
		@Override
		public String toString() {
			return new Gson().toJson(this);
		}
	}
	
	public String getFace_url() {
		return face_url;
	}
	public void setFace_url(String face_url) {
		this.face_url = face_url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public String getMulti_graphic_tup_read_num() {
		return multi_graphic_tup_read_num;
	}
	public void setMulti_graphic_tup_read_num(String multi_graphic_tup_read_num) {
		this.multi_graphic_tup_read_num = multi_graphic_tup_read_num;
	}
	
	public String getWeibo_name() {
		return weibo_name;
	}
	public void setWeibo_name(String weibo_name) {
		this.weibo_name = weibo_name;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getFollowers_count() {
		return followers_count;
	}
	public void setFollowers_count(String followers_count) {
		this.followers_count = followers_count;
	}
	public String getScreen_shot_qr_code() {
		return screen_shot_qr_code;
	}
	public void setScreen_shot_qr_code(String screen_shot_qr_code) {
		this.screen_shot_qr_code = screen_shot_qr_code;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
