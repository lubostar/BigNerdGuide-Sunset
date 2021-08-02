package sk.lubostar.bignerdguide.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val blueSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.blue_sky)
    }
    private val sunsetSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }
    private val nightSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scene.setOnClickListener {
            startAnimation()
        }
    }

    private fun startAnimation() {
        val sunYStart = sun.top.toFloat()
        val sunYEnd = sky.height.toFloat()

        val heightAnimator = ObjectAnimator
                .ofFloat(sun, "y", sunYStart, sunYEnd)
                .setDuration(3000)
        heightAnimator.interpolator = AccelerateInterpolator()

        val sunsetSkyAnimator = ObjectAnimator
                .ofInt(sky, "backgroundColor", blueSkyColor, sunsetSkyColor)
                .setDuration(3000)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val nightSkyAnimator = ObjectAnimator
                .ofInt(sky, "backgroundColor", sunsetSkyColor, nightSkyColor)
                .setDuration(1500)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())

        val animatorSet = AnimatorSet()
        animatorSet.play(heightAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator)
        animatorSet.start()
    }
}