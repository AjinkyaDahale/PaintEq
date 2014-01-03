package com.dahale.ajinkya.painteq;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class OrigEqPadActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        String summary = "<html>\n" +
                "    <head>\n" +
                "<script type=\"text/x-mathjax-config\">\n" +
                "MathJax.Hub.Config({ "
                + "showMathMenu: false, "
                + "jax: ['input/TeX','output/HTML-CSS'], "
                + "extensions: ['tex2jax.js'], "
                + "TeX: { extensions: ['AMSmath.js','AMSsymbols.js',"
                + "'noErrors.js','noUndefined.js'] } "
                + "});</script>" +
                "        <script type=\"text/javascript\" src=\"http://cdn.mathjax.org/mathjax/latest/MathJax.js\"></script>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        \\[\n" +
                "  \\sum_{n=1}^\\infty {1\\over n^2} = {\\pi^2\\over 6}\n" +
                "\\]\n" +
                "    </body>\n" +
                "</html>";
        webView.loadDataWithBaseURL("http://bar", summary, "text/html", "utf-8", "");
    }
}
