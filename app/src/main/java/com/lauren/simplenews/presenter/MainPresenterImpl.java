package com.lauren.simplenews.presenter;

import com.lauren.simplenews.ui.iview.IMainView;
import com.lauren.simplenews.R;
import com.lauren.simplenews.presenter.ipersenter.IMainPresenter;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/13
 */
public class MainPresenterImpl implements IMainPresenter {

    private IMainView mIMainView;

    public MainPresenterImpl(IMainView IMainView) {
        this.mIMainView = IMainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.navigation_item_news:
                mIMainView.switch2News();
                break;
            case R.id.navigation_item_images:
                mIMainView.switch2Images();
                break;
            case R.id.navigation_item_weather:
                mIMainView.switch2Weather();
                break;
            case R.id.navigation_item_about:
                mIMainView.switch2About();
                break;
            default:
                mIMainView.switch2News();
                break;
        }
    }
}
