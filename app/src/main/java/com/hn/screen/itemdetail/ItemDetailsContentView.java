package com.hn.screen.itemdetail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.hn.R;
import com.hn.data.Item;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stevenpungdumri on 7/23/17.
 */

public class ItemDetailsContentView extends NestedScrollView {
    @BindView(R.id.text) TextView mTextView;
    @BindView(R.id.webView) WebView mWebView;

    public ItemDetailsContentView(Context context) {
        super(context);
        init();
    }

    public ItemDetailsContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemDetailsContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setItem(Item item) {
        if (TextUtils.isEmpty(item.getUrl())) {
            String text = item.getText();
            mTextView.setText(Html.fromHtml(text == null ? "No content." : item.getText()));
            mWebView.setVisibility(GONE);
        } else {
            mWebView.loadUrl(item.getUrl());
            mTextView.setVisibility(GONE);
        }
    }

    public boolean onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return false;
    }

    private void init() {
        ButterKnife.bind(inflate(getContext(), R.layout.layout_item_detail_content, this));
        mWebView.setWebViewClient(new OverrideWebviewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
    }

    private class OverrideWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
