package com.juliane.splyza.view.home


import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaSpinnerInteractions.clickSpinnerItem
import com.juliane.splyza.R
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun homeActivityTest() {
        //Verify Home Fragment Views
        assertDisplayed(R.id.tv_invite_title)
        assertDisplayed(R.id.tv_invite_desc)
        assertDisplayed(R.id.b_invite_now)
        assertDisplayed(R.id.iv_invite)

        //Change page to Invite Fragment
        clickOn(R.id.b_invite_now)
        assertDisplayed(R.id.fl_loading)
        assertDisplayed(R.id.iv_loading)

        sleep(2000)

        //Verify Invite Fragment Views
        assertDisplayed(R.id.tv_current_members)
        assertDisplayed(R.id.tv_current_supporters)
        assertDisplayed(R.id.tv_invite_url_desc)
        assertDisplayed(R.id.tv_what_are_permissions)
        assertDisplayed(R.id.b_share_qr)
        assertDisplayed(R.id.b_copy_link)

        sleep(2500)

        assertDisplayed(R.id.tv_current_members_val)
        assertDisplayed(R.id.tv_current_supporters_val)

        assertDisplayed(R.id.s_invite_permissions)
        clickOn(R.id.s_invite_permissions)

        sleep(500)

        clickOn("Player Coach")

        sleep(2500)

        clickOn(R.id.b_copy_link)

        //Change page to QR Fragment
        clickOn(R.id.b_share_qr)
        assertDisplayed(R.id.iv_qr_code)

        sleep(2000)

        //Change page back to Invite Fragment
        clickOn(R.id.tv_menu_back)

        assertDisplayed(R.id.fl_loading)
        assertDisplayed(R.id.iv_loading)

        sleep(2000)

        //Verify Invite Fragment Views
        assertDisplayed(R.id.tv_current_members)
        assertDisplayed(R.id.tv_invite_url_desc)
        assertDisplayed(R.id.tv_what_are_permissions)
        assertDisplayed(R.id.b_share_qr)
        assertDisplayed(R.id.b_copy_link)

        sleep(2500)

        assertDisplayed(R.id.tv_current_members_val)

        assertDisplayed(R.id.s_invite_permissions)
        clickOn(R.id.s_invite_permissions)

        sleep(500)

        clickOn("Player")

        sleep(2500)

        clickOn(R.id.b_copy_link)

        //Change page to QR Fragment
        clickOn(R.id.b_share_qr)
        assertDisplayed(R.id.iv_qr_code)

        sleep(2000)

        //Change page back to Invite Fragment
        clickOn(R.id.tv_menu_back)

        assertDisplayed(R.id.fl_loading)
        assertDisplayed(R.id.iv_loading)

        sleep(2000)

        //Verify Invite Fragment Views
        assertDisplayed(R.id.tv_current_members)
        assertDisplayed(R.id.tv_current_supporters)
        assertDisplayed(R.id.tv_invite_url_desc)
        assertDisplayed(R.id.tv_what_are_permissions)
        assertDisplayed(R.id.b_share_qr)
        assertDisplayed(R.id.b_copy_link)

        sleep(2500)

        assertDisplayed(R.id.tv_current_members_val)
        assertDisplayed(R.id.tv_current_supporters_val)

        assertDisplayed(R.id.s_invite_permissions)
        clickOn(R.id.s_invite_permissions)


        sleep(500)

        clickOn("Supporter")

        sleep(2500)

        clickOn(R.id.b_copy_link)

        //Change page to QR Fragment
        clickOn(R.id.b_share_qr)
        assertDisplayed(R.id.iv_qr_code)

        sleep(2000)

        //Change page back to Invite Fragment
        clickOn(R.id.tv_menu_back)

        assertDisplayed(R.id.fl_loading)
        assertDisplayed(R.id.iv_loading)

        sleep(2000)

        //Verify Invite Fragment Views
        assertDisplayed(R.id.tv_current_members)
        assertDisplayed(R.id.tv_current_supporters)
        assertDisplayed(R.id.tv_invite_url_desc)
        assertDisplayed(R.id.tv_what_are_permissions)
        assertDisplayed(R.id.b_share_qr)
        assertDisplayed(R.id.b_copy_link)

        sleep(2500)

        assertDisplayed(R.id.tv_current_members_val)
        assertDisplayed(R.id.tv_current_supporters_val)

        assertDisplayed(R.id.s_invite_permissions)
        clickOn(R.id.s_invite_permissions)

        sleep(500)

        clickOn("Coach")

        sleep(2500)

        clickOn(R.id.b_copy_link)

        //Change page to QR Fragment
        clickOn(R.id.b_share_qr)
        assertDisplayed(R.id.iv_qr_code)

        sleep(2000)

        //Change page back to Invite Fragment
        clickOn(R.id.tv_menu_back)

        sleep(2000)

        //Change page back to Home Fragment
        clickOn(R.id.tv_menu_back)

        //Verify Home Fragment Views
        assertDisplayed(R.id.tv_invite_title)
        assertDisplayed(R.id.tv_invite_desc)
        assertDisplayed(R.id.b_invite_now)
        assertDisplayed(R.id.iv_invite)
    }
}
