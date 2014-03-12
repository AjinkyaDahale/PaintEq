package com.dahale.ajinkya.painteq;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.*;
import com.dahale.ajinkya.painteq.utils.RepeatListener;

import java.util.ArrayList;

public class EqPadActivity extends FragmentActivity
        implements View.OnClickListener, SymbolsGridAdapter.KeyClickListener {

    /* Various views that will be needed in more than a few methods.*/
    private LinearLayout parentLayout;
    private EditText content;
    private LinearLayout symbolsCover;

    // TODO: Perhaps these two could do well with their own classes
    private View popUpView;
    private PopupWindow popupWindow;

    /* This to match our custom sliding pager based keyboard with the device's
     * original. Feels like a quick hack, but at least has a few methods in place. */
    private int keyboardHeight;
    private boolean isKeyBoardVisible;

    /**
     * This field is used in checking keyboard height. Better understanding would help.
     */
    private int previousHeightDifference = 0;

    /* Helper functions. */

    /**
     * Code to insert a second backslash before any backslash already present
     * (Something possibly avoidable through other means).
     */
    private String doubleEscapeTeX(String s) {
        String t = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\'') t += '\\';
            if (s.charAt(i) != '\n') t += s.charAt(i);
            if (s.charAt(i) == '\\') t += "\\";
        }
        return t;
    }

    private int exampleIndex = 0;

    private String getExample(int index) {
        return getResources().getStringArray(R.array.tex_examples)[index];
    }

    /**
     * Defining all components of paths keyboard
     */
    private void enablePopUpView() {

        final Spinner spinner = (Spinner) popUpView.findViewById(R.id.symbolGroups_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.symbolGroups_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);

        final ViewPager pager = (ViewPager) popUpView.findViewById(R.id.symbols_pager);
        pager.setOffscreenPageLimit(3);

        ArrayList<String> paths = new ArrayList<String>();

        addSymbols(paths);

        SymbolsPagerAdapter adapter = new SymbolsPagerAdapter(this, paths, this);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                spinner.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pager.setCurrentItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Creating a pop window for paths keyboard
        popupWindow = new PopupWindow(popUpView, LayoutParams.MATCH_PARENT,
                keyboardHeight, false);

//        popUpView.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
//                content.dispatchKeyEvent(event);
//            }
//        });

        popUpView.findViewById(R.id.back).setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                content.dispatchKeyEvent(event);
            }
        }));

        popUpView.findViewById(R.id.add_symbol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new CustomSymbolDialogFragment("Type new symbol here:","\\")).show(EqPadActivity.this.getSupportFragmentManager(),"tag");
            }
        });

//        popUpView.findViewById(R.id.back).setOnLongClickListener(new View.OnLongClickListener() {
//
//            @Override
//            public boolean onLongClick(View v) {
//                KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
//                content.dispatchKeyEvent(event);
//                return false;
//            }
//        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                symbolsCover.setVisibility(LinearLayout.GONE);
            }
        });
    }

    /**
     * This method right now is a really rash hack. It is to
     * add
     */
    private void addSymbols(ArrayList<String> paths) {
        int numberOfSymbols = 65;
        for (short i = 1; i <= numberOfSymbols; i++) {
            if (i < 10) paths.add("img00" + i + "greek.png");
            else if (i < 100) paths.add("img0" + i + "greek.png");
        }
    }

    /**
     * Change the height of our custom keyboard.
     *
     * @param popUpHeight Desired height
     */
    private void changeKeyboardHeight(int popUpHeight) {
        if (popUpHeight > 100) {
            keyboardHeight = popUpHeight;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, keyboardHeight);
            symbolsCover.setLayoutParams(params);

        }
    }

    // FIXME: There is still a bug: 1) press Sym button; 2) click on EditText; misbehaves.
    private void checkKeyboardHeight(final View parentLayout) {

        //noinspection ConstantConditions
        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        Rect r = new Rect();
                        parentLayout.getWindowVisibleDisplayFrame(r);

                        @SuppressWarnings("ConstantConditions") int screenHeight = parentLayout.getRootView()
                                .getHeight();
                        int heightDifference = screenHeight - (r.bottom);

                        if (previousHeightDifference - heightDifference > 50) {
                            popupWindow.dismiss();
                        }

                        previousHeightDifference = heightDifference;
                        if (heightDifference > 100) {
                            isKeyBoardVisible = true;
//                            symbolsCover.setVisibility(LinearLayout.GONE);
                            changeKeyboardHeight(heightDifference);
                            popupWindow.setHeight(keyboardHeight);
                        } else {
                            isKeyBoardVisible = false;
                        }

                    }
                });

    }

    /* Overridden methods for custom behaviour */

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        parentLayout = (LinearLayout) findViewById(R.id.parent_view);

        WebView w = (WebView) findViewById(R.id.webview);
        w.getSettings().setJavaScriptEnabled(true);
        w.getSettings().setBuiltInZoomControls(true);
        w.loadDataWithBaseURL("http://bar", "<script type='text/x-mathjax-config'>"
                + "MathJax.Hub.Config({ "
                + "showMathMenu: false, "
                + "jax: ['input/TeX','output/HTML-CSS'], "
                + "extensions: ['tex2jax.js'], "
                + "TeX: { extensions: ['AMSmath.js','AMSsymbols.js',"
                + "'noErrors.js','noUndefined.js'] } "
                + "});</script>"
                + "<script type='text/javascript' "
                + "src='file:///android_asset/MathJax/MathJax.js'"
                + "></script><span id='math'></span>", "text/html", "utf-8", "");

        content = (EditText) findViewById(R.id.edit);
        content.setBackgroundColor(Color.LTGRAY);
        content.setTextColor(Color.BLACK);

        checkKeyboardHeight(parentLayout);
        /** Occupies the space instead of a keyboard if it is not active. */
        symbolsCover = (LinearLayout) findViewById(R.id.footer_for_symbols);

        content.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (symbolsCover.getVisibility() == LinearLayout.VISIBLE) {
                    symbolsCover.setVisibility(LinearLayout.GONE);
                }

            }
        });

//        /**
//         * To be used if you want rendered image to change at every edit.
//         */
//        content.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//        });

        /** Our custom input view comes as a popup. */
        popUpView = getLayoutInflater().inflate(R.layout.symbols_popup, null);

        // Defining default height of keyboard which is equal to 230 dip
        final float popUpHeight = getResources().getDimension(
                R.dimen.keyboard_height);
        changeKeyboardHeight((int) popUpHeight);

        enablePopUpView();

//        e.requestFocus();

//        findViewById(R.id.button2).setOnClickListener(this);
//        findViewById(R.id.button3).setOnClickListener(this);
//        findViewById(R.id.button4).setOnClickListener(this);
//        findViewById(R.id.puttextbutton).setOnClickListener(this);
        findViewById(R.id.compile_button).setOnClickListener(this);

        findViewById(R.id.compile_button).setBackgroundColor(Color.LTGRAY);

        ImageView symButton = (ImageView) findViewById(R.id.symbols_button);
        symButton.setBackgroundColor(Color.LTGRAY);
        symButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Toast.makeText(EqPadActivity.this, "Symbols Keyboard", Toast.LENGTH_SHORT).show();

                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    content.requestFocus();

                    if (isKeyBoardVisible)//(symbolsCover.getVisibility() == LinearLayout.GONE)
                        symbolsCover.setVisibility(LinearLayout.GONE);
                    else symbolsCover.setVisibility(LinearLayout.VISIBLE);

                    popupWindow.setHeight(keyboardHeight);
                    popupWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0);

                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.code_edit_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        WebView w = (WebView) findViewById(R.id.webview);
        EditText e = (EditText) findViewById(R.id.edit);
        switch (item.getItemId()) {
            case R.id.clear_code:
                e.setText("");
                w.loadUrl("javascript:document.getElementById('math').innerHTML='';");
                w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
                return true;

            case R.id.show_example:
                e.setText(getExample(exampleIndex++));
                if (exampleIndex > getResources().getStringArray(R.array.tex_examples).length - 1)
                    exampleIndex = 0;
                //noinspection ConstantConditions
                w.loadUrl("javascript:document.getElementById('math').innerHTML='\\\\["
                        + doubleEscapeTeX(e.getText().toString())
                        + "\\\\]';");
                w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Overriding onKeyDown for dismissing keyboard on key down
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (popupWindow.isShowing() && keyCode == KeyEvent.KEYCODE_BACK) {
            popupWindow.dismiss();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /* Implementations of interfaces */

    @Override
    public void keyClickedIndex(String index) {
        // this is to get the the cursor position
        int start = content.getSelectionStart();
        String s = ((start == 0) ? "" : " ") + index;
        // this will get the text and insert the String s into   the current position
        //noinspection ConstantConditions
        content.getText().insert(start, s);
//        content.getText().insert(start, "test");
//        content.setSelection(start + "test".length());
    }

    @Override
    public void onClick(View v) {
        WebView w = (WebView) findViewById(R.id.webview);
        if (/*v == findViewById(R.id.button2) || */ v == findViewById(R.id.compile_button)) {
            //noinspection ConstantConditions
            w.loadUrl("javascript:document.getElementById('math').innerHTML='\\\\["
                    + doubleEscapeTeX(content.getText().toString()) + "\\\\]';");
            w.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
        }
    }

}