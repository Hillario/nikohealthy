package nikohealthy.com.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_main{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.5d);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3d);
views.get("panel1").vw.setWidth((int)((100d / 100 * width)));
views.get("panel1").vw.setHeight((int)((100d / 100 * height)));
views.get("imageview2").vw.setTop((int)((0d / 100 * height)+(10d * scale)));
views.get("imageview2").vw.setLeft((int)((10d / 100 * width)));
views.get("imageview2").vw.setLeft((int)((100d / 100 * width) - (views.get("imageview2").vw.getWidth())));
views.get("imageview2").vw.setLeft((int)((50d / 100 * width) - (views.get("imageview2").vw.getWidth() / 2)));
views.get("imageview1").vw.setLeft((int)((50d / 100 * width) - (views.get("imageview1").vw.getWidth() / 2)));
views.get("panel2").vw.setLeft((int)((50d / 100 * width) - (views.get("panel2").vw.getWidth() / 2)));
views.get("panel3").vw.setLeft((int)((50d / 100 * width) - (views.get("panel3").vw.getWidth() / 2)));
views.get("btnlogin").vw.setLeft((int)((50d / 100 * width) - (views.get("btnlogin").vw.getWidth() / 2)));
//BA.debugLineNum = 19;BA.debugLine="btnforgotpass.HorizontalCenter=50%x"[main/General script]
views.get("btnforgotpass").vw.setLeft((int)((50d / 100 * width) - (views.get("btnforgotpass").vw.getWidth() / 2)));
//BA.debugLineNum = 20;BA.debugLine="btnsignup.HorizontalCenter=50%x"[main/General script]
views.get("btnsignup").vw.setLeft((int)((50d / 100 * width) - (views.get("btnsignup").vw.getWidth() / 2)));
//BA.debugLineNum = 21;BA.debugLine="lblshow.HorizontalCenter=50%x"[main/General script]
views.get("lblshow").vw.setLeft((int)((50d / 100 * width) - (views.get("lblshow").vw.getWidth() / 2)));

}
}