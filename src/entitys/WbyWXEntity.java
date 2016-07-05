package entitys;

import com.google.gson.Gson;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/7/4.
 */
public class WbyWXEntity {

    /**
     * weibo_id : TTXuanPai
     * weibo_type : 9
     * weibo_name : 天天炫拍
     * url : /single?weibo_id=TTXuanPai&weibo_type=9&sign=f25b4d9109
     * face_url : http://img.weiboyi.com/img/uploadimg/weixin_follower_img/1450319160323223553756721d389a9c6.jpg
     * followers_count : 600万
     * age : null
     * gender : 3
     * is_online : 2
     * introduction : 免费、酷炫、易用的3D音乐照片秀!简单几步,马上让您的照片炫起来!
     * verification_info : 微信认证：深圳天天炫拍科技有限公司
     * is_exclusive : 2
     * area_id : 0
     * area_name :
     * location : null
     * profit_rate : 0.05
     * profit_grade : 200
     * is_vip : 1
     * is_bluevip : -1
     * is_daren : -1
     * is_shield : 2
     * is_sensitive : 2
     * flag : 0
     * is_famous : 1
     * is_extend : 1
     * owner_admin_id : 332
     * follower_be_identified : 2
     * follower_be_identified_time : null
     * audience_profile :
     * active_score : 0.00
     * last_active_time : 1466382079
     * is_promotion : false
     * promotion_comment : false
     * promotion_begin_time : 2015
     * screen_shot_followers_last_updated_time : 1458964511
     * industry_type : null
     * screen_portrait : http://img.weiboyi.com/img/uploadimg/weixin_follower_img/1450319160323223553756721d389a9c6.jpg
     * screen_shot_followers : http://img.weiboyi.com/img/uploadimg/weixin_follower_img/1450319154323223553756721d327d23e.jpg
     * screen_shot_qr_code : http://img.weiboyi.com/img/uploadimg/weixin_follower_img/1450319165323223553756721d3dcd15f.jpg
     * single_graphic_price : null
     * multi_graphic_top_price : 83200.00
     * multi_graphic_second_price : 57200.00
     * snbt_exponent : 93.77
     * single_graphic_read_num : null
     * multi_graphic_top_read_num : 100001
     * multi_graphic_second_read_num : 100001
     * multi_graphic_other_read_num : 98888
     * graphic_send_num : 7
     * cooperation_degree : 50.00
     * account_id : 460726
     * id : 25394
     * pack_type : 2
     * pack_name : 天天炫拍
     * pack_info : {"pack_name":"天天炫拍","pack_type":2,"face_url":"http://img.weiboyi.com/img/selfMedia/1458964594323223553756f60872c05ba.jpg","brief_introduction":"免费、酷炫、易用的3D音乐照片秀!简单几步,马上让您的照片炫起来!","reservation_notice":"免费、酷炫、易用的3D音乐照片秀!简单几步,马上让您的照片炫起来!","choose_me_reason":"免费、酷炫、易用的3D音乐照片秀!简单几步,马上让您的照片炫起来!","success_case":"","industry":"","history_case":"","full_introduction":"","web_site":"","style":"","effect":"","history":"","related_url":"","pack_id":18502,"related_url_a":""}
     * domains : 情感心理 创意/生活
     * professions :
     * cooperation_index : 高
     * reference_price_range_default : {"min":null,"max":null,"update_time":"2016-03-22 18:47:02"}
     * reference_price_range_multi_top : {"min":null,"max":null,"update_time":"2016-06-27 17:11:47"}
     * reference_price_range_multi_second : {"min":null,"max":null,"update_time":"2016-06-27 17:11:47"}
     * reference_price_range_multi_other : {"min":null,"max":null,"update_time":"2015-12-17 10:34:08"}
     * reference_price_range_single : {"min":null,"max":null,"update_time":"2015-12-17 10:34:08"}
     * work_nature :
     * company_name :
     * work_position :
     * school_name :
     * work_nature_be_identified :
     * work_nature_be_identified_time :
     * creations : {"39843":{"pack_id":18502,"creation_type_id":1,"selling_point":"行业级写手，帮助麦当劳、戴尔、百事等公司出稿，多次在行业大号上发布。\r\n多次十万+阅读\r\n提供大纲。可以修改文案。\r\n请提前三天给到素材。","referenced_price":"2000.00","urgent":2,"provide_outline":1,"is_online":1,"creation_id":39843,"cType":"原创写稿"}}
     * pyq_account_daily_update_images : []
     * is_black : false
     * external_reference_price : {"multi_top":{"quote":100430},"multi_second":{"quote":68070},"multi_other":{"quote":0},"single":{"quote":0},"original_writing":{"quote":0}}
     * reference_price : {"multi_top":{"quote":100430},"multi_second":{"quote":68070},"multi_other":{"quote":0},"single":{"quote":0},"original_writing":{"quote":0}}
     * net_deal_price : null
     * orders_weekly : 0
     * orders_monthly : 0
     * tactics_info : leave
     * can_origin : 1
     * has_pyq_daily_images : 1
     */

	private String screen_shot_qr_code;
    private String weibo_id;
    private String weibo_name;
    private String face_url;
    private String followers_count;
    private String introduction;
    private String verification_info;
    private int area_id;
    private String area_name;
    private String industry_type;
    private int account_id;
    private String id;
    /**
     * multi_top : {"quote":100430}
     * multi_second : {"quote":68070}
     * multi_other : {"quote":0}
     * single : {"quote":0}
     * original_writing : {"quote":0}
     */

    private ReferencePriceBean reference_price;

    public String getScreen_shot_qr_code() {
		return screen_shot_qr_code;
	}

	public void setScreen_shot_qr_code(String screen_shot_qr_code) {
		this.screen_shot_qr_code = screen_shot_qr_code;
	}

	public String getWeibo_id() {
        return weibo_id;
    }

    public void setWeibo_id(String weibo_id) {
        this.weibo_id = weibo_id;
    }

    public String getWeibo_name() {
        return weibo_name;
    }

    public void setWeibo_name(String weibo_name) {
        this.weibo_name = weibo_name;
    }

    public String getFace_url() {
        return face_url;
    }

    public void setFace_url(String face_url) {
        this.face_url = face_url;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(String followers_count) {
        this.followers_count = followers_count;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getVerification_info() {
        return verification_info;
    }

    public void setVerification_info(String verification_info) {
        this.verification_info = verification_info;
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

    public String getIndustry_type() {
        return industry_type;
    }

    public void setIndustry_type(String industry_type) {
        this.industry_type = industry_type;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReferencePriceBean getReference_price() {
        return reference_price;
    }

    public void setReference_price(ReferencePriceBean reference_price) {
        this.reference_price = reference_price;
    }

    public static class ReferencePriceBean {
        /**
         * quote : 100430
         */

        private MultiTopBean multi_top;
        /**
         * quote : 68070
         */

        private MultiSecondBean multi_second;
        /**
         * quote : 0
         */

        private MultiOtherBean multi_other;
        /**
         * quote : 0
         */

        private SingleBean single;
        /**
         * quote : 0
         */

        private OriginalWritingBean original_writing;

        public MultiTopBean getMulti_top() {
            return multi_top;
        }

        public void setMulti_top(MultiTopBean multi_top) {
            this.multi_top = multi_top;
        }

        public MultiSecondBean getMulti_second() {
            return multi_second;
        }

        public void setMulti_second(MultiSecondBean multi_second) {
            this.multi_second = multi_second;
        }

        public MultiOtherBean getMulti_other() {
            return multi_other;
        }

        public void setMulti_other(MultiOtherBean multi_other) {
            this.multi_other = multi_other;
        }

        public SingleBean getSingle() {
            return single;
        }

        public void setSingle(SingleBean single) {
            this.single = single;
        }

        public OriginalWritingBean getOriginal_writing() {
            return original_writing;
        }

        public void setOriginal_writing(OriginalWritingBean original_writing) {
            this.original_writing = original_writing;
        }

        public static class MultiTopBean {
            private int quote;

            public int getQuote() {
                return quote;
            }

            public void setQuote(int quote) {
                this.quote = quote;
            }
        }

        public static class MultiSecondBean {
            private int quote;

            public int getQuote() {
                return quote;
            }

            public void setQuote(int quote) {
                this.quote = quote;
            }
        }

        public static class MultiOtherBean {
            private int quote;

            public int getQuote() {
                return quote;
            }

            public void setQuote(int quote) {
                this.quote = quote;
            }
        }

        public static class SingleBean {
            private int quote;

            public int getQuote() {
                return quote;
            }

            public void setQuote(int quote) {
                this.quote = quote;
            }
        }

        public static class OriginalWritingBean {
            private int quote;

            public int getQuote() {
                return quote;
            }

            public void setQuote(int quote) {
                this.quote = quote;
            }
        }
    }
    
    @Override
    public String toString() {
    	return new Gson().toJson(this);
    }
}
