package cn.justwithme.withme;

import android.util.Log;

import org.junit.Test;

import cn.justwithme.withme.Utils.HttpUtils;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String resoult = HttpUtils.doPostRequest("UserService","{\"userPassword\":\"14121047\",\"userName\":\"14121047\"}");
        Log.e("TAG","---------------------------------------------");
        Log.e("TAG",resoult);
        Log.e("TAG","---------------------------------------------");
    }
}