package com.lauren.simplenews.model.imodel;

import com.lauren.simplenews.model.OnLoadNewsDetailListener;
import com.lauren.simplenews.model.OnLoadNewsListListener;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/19
 */
public interface INewsModel {

    void loadNews(String url, int type, OnLoadNewsListListener listener);

    void loadNewsDetail(String docid, OnLoadNewsDetailListener listener);

}
