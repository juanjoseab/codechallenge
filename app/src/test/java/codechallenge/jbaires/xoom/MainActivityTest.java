package codechallenge.jbaires.xoom;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;

import codechallenge.jbaires.xoom.adapters.CountryAdapter;
import codechallenge.jbaires.xoom.models.Country;
import codechallenge.jbaires.xoom.ui.MainActivity;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP, manifest = "src/main/AndroidManifest.xml")
public class MainActivityTest {

    MainActivity mainActivity;
    CountryAdapter countryAdapter;
    CountryAdapter.ViewHolderCountry holderCountry;
    View mockView;



    @Before
    public void setUp(){
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        countryAdapter = new CountryAdapter();
        mockView = mock(View.class);
    }


    @Test
    public void countryListIsPopulated() throws Exception {
        //Arrange
        Integer actualSize = mainActivity.countries.size();




        //Act
        mainActivity.getCountries();
        Integer newSize = mainActivity.countries.size();


        //Assert
        assertThat("Integer", newSize, greaterThan(actualSize) );
    }



}
