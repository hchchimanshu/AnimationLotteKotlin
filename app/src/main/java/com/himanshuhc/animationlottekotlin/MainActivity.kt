package com.himanshuhc.animationlottekotlin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable

class MainActivity : AppCompatActivity() {

    var animationView: LottieAnimationView? = null
    private val animations = arrayOf<Int>(R.raw.screen_1s, R.raw.screen_1_2_transitions,
        R.raw.screen_2s, R.raw.screen_2_3_transitions, R.raw.screen_3s, R.raw.screen_3_4_transitions,
        R.raw.screen_4s, R.raw.screen_4_5_transitions, R.raw.screen_5, R.raw.screen_5_6_transitions,
        R.raw.screen_6s, R.raw.screen_6_7_transitions, R.raw.screen_7s , R.raw.animation_blank)
    private val revAnimation = arrayOf<Int>(R.raw.screen_1s,R.raw.screen_2_1_transitions,
        R.raw.screen_2s, R.raw.screen_3_2_transitions, R.raw.screen_3s,R.raw.screen_4_3_transitions,
        R.raw.screen_4s, R.raw.screen_5_4_transitions, R.raw.screen_5, R.raw.screen_6_5_transitions,
        R.raw.screen_6s, R.raw.screen_7_6_transitions, R.raw.screen_7s ,R.raw.animation_blank)
    private var trainingScreenLayout: ConstraintLayout? = null
    private var countTextView:android.widget.TextView? = null
    private var nextButton:android.widget.TextView? = null
    private var prevButton:android.widget.TextView? = null
    private var dotsLayout: LinearLayout? = null
    private var backwardAnimations = false
    private var addListener = false
    private var indexCount = 0
    private var countScreen = 1
    private lateinit var dots: Array<TextView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        May face crash during swipe - follow : https://stackoverflow.com/a/32245018/15614129
         */

        animationView = findViewById<LottieAnimationView>(R.id.animation_lotte)
        prevButton = findViewById<Button>(R.id.button)
        nextButton = findViewById<Button>(R.id.button2)
        trainingScreenLayout = findViewById<ConstraintLayout>(R.id.traning_screen_CL)

        countTextView = findViewById(R.id.swiping_counts_TV)
        dotsLayout = findViewById(R.id.dotss_LL)

        //setting up the first animation
        animationView!!.enableMergePathsForKitKatAndAbove(true)
        setLottieAnimation(animationView)
        countTextView!!.text = "1/8"


        addBottomDots(1)

        getGesturesfromTouch()

        nextButton?.setOnClickListener {
            forwardSwipe()
        }

        prevButton?.setOnClickListener(View.OnClickListener {
            reverseSwipe()
        })
    }

    private fun addBottomDots(currentPage: Int) {
        dots = Array(animations.size - 4) { TextView(this) }
        val colorsActive = resources.getIntArray(R.array.array_dot_inactive)
        val colorsInactive = resources.getIntArray(R.array.array_dot_active)
        dotsLayout!!.removeAllViews()
        for (i in 1 until dots.size - 1) {
            dots[i].text = Html.fromHtml("&#8226;")
            dots[i].setTextSize(35f)
            dots[i].setTextColor(colorsInactive[currentPage])
            dotsLayout!!.addView(dots[i])
        }
        if (dots.size > 0) dots[currentPage].setTextColor(colorsActive[currentPage])
    }


    private fun reverseSwipe() {
        if (indexCount != 0) {
            indexCount--
            if (countScreen != 1)
                countScreen--
            addBottomDots(countScreen)
            backwardAnimations = true
            addListener = true
            if (indexCount!=12)
            else {
                animationView!!.animate().alpha(1.0f).duration = 1100
            }
            setLottieAnimation(animationView)

            countTextView!!.text = "$countScreen/8"
            countTextView!!.visibility = View.VISIBLE
            countTextView!!.animate().alpha(0.0f).setDuration(0)
            countTextView!!.animate().alpha(1.0f).setDuration(800)
            nextButton!!.visibility = View.VISIBLE
            dotsLayout!!.setVisibility(View.VISIBLE)
        }
    }

    private fun forwardSwipe() {
        if (indexCount < 11) {
            indexCount++
            countScreen++
            addBottomDots(countScreen)
            backwardAnimations = false
            addListener = true
            setLottieAnimation(animationView)
            countTextView!!.text = "$countScreen/8"
            countTextView!!.visibility = View.VISIBLE
            nextButton!!.visibility = View.VISIBLE
            dotsLayout!!.setVisibility(View.VISIBLE)
        }
    }

    private fun setLottieAnimation(lottieView: LottieAnimationView?) {
        lottieView?.apply {

            val loopListener: Animator.AnimatorListener = object : AnimatorListenerAdapter() {
                override fun onAnimationRepeat(animation: Animator) {
                    if (addListener) {
                        if(indexCount!=12 || (indexCount==12 && !backwardAnimations)) {
                            LottieCompositionFactory.fromRawResSync(
                                this@MainActivity,
                                animations[indexCount]
                            )?.let { result ->
                                result.value?.let { it -> setComposition(it) }
                            }

                            repeatCount = LottieDrawable.INFINITE
                            playAnimation()
                        }
                        addListener = false
                    }

                }
            }



            if (backwardAnimations) {
                LottieCompositionFactory.fromRawResSync(this@MainActivity, revAnimation[indexCount])?.let { result ->
                    result.value?.let { it -> setComposition(it) }
                }
                if(indexCount!=12) {
                    indexCount--
                    repeatCount = 1
                }else{

                    repeatCount = LottieDrawable.INFINITE
                }
                playAnimation()
            }
            else
            {
                LottieCompositionFactory.fromRawResSync(this@MainActivity, animations[indexCount])?.let { result ->
                    result.value?.let { it -> setComposition(it) }
                }
                repeatCount = 1
                if(indexCount!=0)
                    indexCount++
                playAnimation()
            }
            if(addListener) {
                playAnimation()
                addAnimatorListener(loopListener)
            }
        }
    }

    private fun getGesturesfromTouch() {
        trainingScreenLayout!!.setOnTouchListener(object :
            OnSwipeTouchListener(this) {
            override fun onSwipeTop() {
//                Toast.makeText(SwipeTrainingScreen.this, "top", Toast.LENGTH_SHORT).show();
            }

            override fun onSwipeRight() {
//                Log.d(TAG, "onSwipeRight: "+indexCount)
                reverseSwipe()
            }

            override fun onSwipeLeft() {
//                Log.d(TAG, "onSwipeLeft: "+indexCount)

                forwardSwipe()
            }

            override fun onSwipeBottom() {

//                Toast.makeText(SwipeTrainingScreen.this, "bottom", Toast.LENGTH_SHORT).show();
            }
        })
    }

}