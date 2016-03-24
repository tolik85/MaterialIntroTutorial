package za.co.riggaroo.materialhelptutorial.tutorial;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import za.co.riggaroo.materialhelptutorial.MaterialTutorialFragment;
import za.co.riggaroo.materialhelptutorial.R;
import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.adapter.MaterialTutorialAdapter;
import za.co.riggaroo.materialhelptutorial.view.CirclePageIndicator;


public class MaterialTutorialActivity extends AppCompatActivity implements MaterialTutorialContract.View {

    private static final String TAG = "MaterialTutActivity";
    public static final String MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS = "tutorial_items";
    private ViewPager mHelpTutorialViewPager;
    private View mRootView;
    private TextView mTextViewSkip;
    private ImageButton mNextButton;
    private Button mDoneButton;
    private MaterialTutorialPresenter materialTutorialPresenter;
    private ImageView arrow1;
    private ImageView arrow2;
    private ImageView arrow3;
    private ImageView arrow4;
    private ImageView arrow5;
    private android.os.Handler handler;
    private static final int delay = 200;

    private View.OnClickListener finishTutorialClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            materialTutorialPresenter.doneOrSkipClick();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_tutorial);

        materialTutorialPresenter = new MaterialTutorialPresenter(this, this);
        setStatusBarColor();
        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            getActionBar().hide();
        }
        mRootView = findViewById(R.id.activity_help_root);
        mHelpTutorialViewPager = (ViewPager) findViewById(R.id.activity_help_view_pager);
        mTextViewSkip = (TextView) findViewById(R.id.activity_help_skip_textview);
        mNextButton = (ImageButton) findViewById(R.id.activity_next_button);
        mDoneButton = (Button) findViewById(R.id.activity_tutorial_done);

        mTextViewSkip.setOnClickListener(finishTutorialClickListener);
        mDoneButton.setOnClickListener(finishTutorialClickListener);



        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialTutorialPresenter.nextClick();


            }
        });
        List<TutorialItem> tutorialItems = getIntent().getParcelableArrayListExtra(MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS);
        materialTutorialPresenter.loadViewPagerFragments(tutorialItems);

        arrow1 = (ImageView) findViewById(R.id.arrow_1);
        arrow2 = (ImageView) findViewById(R.id.arrow_2);
        arrow3 = (ImageView) findViewById(R.id.arrow_3);
        arrow4 = (ImageView) findViewById(R.id.arrow_4);
        arrow5 = (ImageView) findViewById(R.id.arrow_5);

        handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

//                spring.setEndValue(1);
                showArrow(arrow1);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        showArrow(arrow2);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showArrow(arrow3);
                            }
                        }, delay);
                    }
                }, delay);
            }
        }, delay);


    }

    private void showArrow(ImageView arrow) {
        arrow.setVisibility(View.VISIBLE);
        Animation expandIn = AnimationUtils.loadAnimation(MaterialTutorialActivity.this, R.anim.expand_in);
        arrow.startAnimation(expandIn);
    }

    private void hideArrow(final ImageView arrow) {
        Animation expandIn = AnimationUtils.loadAnimation(MaterialTutorialActivity.this, R.anim.expand_out);
        arrow.startAnimation(expandIn);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                arrow.setVisibility(View.GONE);
            }
        }, delay);
    }


    private void setStatusBarColor() {
        if (isFinishing()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void showNextTutorial() {
        int currentItem = mHelpTutorialViewPager.getCurrentItem();
        if (currentItem < materialTutorialPresenter.getNumberOfTutorials()) {
            mHelpTutorialViewPager.setCurrentItem(mHelpTutorialViewPager.getCurrentItem() + 1);
        }
    }

    @Override
    public void showEndTutorial() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setBackgroundColor(int color) {
        mRootView.setBackgroundColor(color);
    }

    @Override
    public void showDoneButton() {
        mTextViewSkip.setVisibility(View.INVISIBLE);
        mNextButton.setVisibility(View.GONE);
        mDoneButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSkipButton() {
        mTextViewSkip.setVisibility(View.VISIBLE);
        mNextButton.setVisibility(View.VISIBLE);
        mDoneButton.setVisibility(View.GONE);
    }

    @Override
    public void setViewPagerFragments(List<MaterialTutorialFragment> materialTutorialFragments) {
        mHelpTutorialViewPager.setAdapter(new MaterialTutorialAdapter(getSupportFragmentManager(), materialTutorialFragments));
        CirclePageIndicator mCirclePageIndicator = (CirclePageIndicator) findViewById(R.id.activity_help_view_page_indicator);

        mCirclePageIndicator.setViewPager(mHelpTutorialViewPager);
        mCirclePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                materialTutorialPresenter.onPageSelected(mHelpTutorialViewPager.getCurrentItem());

                switch (i) {
                    case 0: {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                hideArrow(arrow4);

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showArrow(arrow1);

                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                showArrow(arrow2);

                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showArrow(arrow3);
                                                    }
                                                }, delay);
                                            }
                                        }, delay);

                                    }
                                }, delay);
                            }
                        }, delay);

                        break;
                    }

                    case 1: {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if (arrow5.getVisibility() == View.VISIBLE)
                                    hideArrow(arrow5);
                                else {
                                    hideArrow(arrow1);
                                    hideArrow(arrow2);
                                    hideArrow(arrow3);
                                }

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showArrow(arrow4);
                                    }
                                }, delay);
                            }
                        }, delay);

                        break;
                    }

                    case 2: {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                hideArrow(arrow4);

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showArrow(arrow5);
                                    }
                                }, delay);
                            }
                        }, delay);

                        break;
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mHelpTutorialViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
                    @Override
                    public void transformPage(View page, float position) {
                        materialTutorialPresenter.transformPage(page, position);
                    }
                }

        );
    }
}
